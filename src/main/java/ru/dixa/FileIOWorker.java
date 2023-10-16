package ru.dixa;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileIOWorker {

    private static String fileNaming() {
        return "save" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".dat";
    }

    public static void saveToFile(GameProgress progress) {

        File file = new File(Settings.SAVEGAME_PATH + File.separator + fileNaming());

        if (!file.exists()) {
            try {
                file.createNewFile();
                ObjectOutputStream saveToFileStream = new ObjectOutputStream(new FileOutputStream(file));
                saveToFileStream.writeObject(progress);
                saveToFileStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static GameProgress loadFromFile(Path path) {

        GameProgress gameProgress = null;

        File progressFile = new File(String.valueOf(path));

        try {
            FileInputStream loadFileStream = new FileInputStream(progressFile);
            ObjectInputStream objectStream = new ObjectInputStream(loadFileStream);
            gameProgress = (GameProgress) objectStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        return gameProgress;
    }

    public static boolean createSource(Path path) {

        if (String.valueOf(path).contains(".")) {                   // проверка файл или каталог =)
            return createFile(path);
        } else {
            return createDirectory(path);
        }
    }

    public static boolean createFile(Path path) {

        File file = new File(String.valueOf(path));
        try {
            file.createNewFile();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean createDirectory(Path path) {

        try {
            Files.createDirectories(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Ищет файлы и папки по заданному пути исключая zip.zip
    public static List<String> getFilesInDirectory(Path path) {
        File fileName = new File(String.valueOf(path));
        File[] fileList = fileName.listFiles();
        List<String> foundedFiles = Arrays
                .stream(fileList)
                .map(File::getAbsolutePath)
                .filter((x -> !x.contains("zip.zip")))
                .toList();
        return foundedFiles;
    }

    public static void zipFiles(Path path) {

        List<String> filesToZip = getFilesInDirectory(path);

        try {
            ZipOutputStream zipFileStream = new ZipOutputStream(new FileOutputStream(String.valueOf(path.resolve("zip.zip"))));

            for (String fileToZip : filesToZip) {

                ZipEntry zipEntry = new ZipEntry(String.valueOf(Path.of(fileToZip).getFileName()));
                FileInputStream inputStream = new FileInputStream(fileToZip);
                zipFileStream.putNextEntry(zipEntry);

                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);

                zipFileStream.write(buffer);
                zipFileStream.closeEntry();
                inputStream.close();
            }
            zipFileStream.finish();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFiles(Path path){

        List<String> filesToZip = getFilesInDirectory(path);

        for (String file : filesToZip){
            try {
                Files.deleteIfExists(Path.of(file));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void unzipFiles(Path path) {

        ZipInputStream zipFileStream = null;
        ZipEntry entry;

        try {
            zipFileStream = new ZipInputStream(new FileInputStream(String.valueOf(path.resolve("zip.zip"))));

            while ((entry = zipFileStream.getNextEntry()) != null) {

                String fileName = entry.getName();
                String pathToUnzip = path + File.separator + fileName;

                FileOutputStream unZippedStream = new FileOutputStream(pathToUnzip);
                byte[] buffer = zipFileStream.readAllBytes();

                unZippedStream.write(buffer);
                unZippedStream.flush();
                zipFileStream.closeEntry();
            }
            zipFileStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}