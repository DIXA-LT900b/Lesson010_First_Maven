package dixa.task3.openfromzip;

import dixa.FileIOWorker;
import dixa.GameProgress;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

import static dixa.Settings.SAVEGAME_PATH;

public class Task3UnZip {

    public static void main(String[] args) {

        FileIOWorker.unzipFiles(SAVEGAME_PATH);

        List<String> fileList = FileIOWorker.getFilesInDirectory(SAVEGAME_PATH);

        Random random = new Random();
        Path fileToLoad = Path.of(fileList.get(random.nextInt(fileList.size())));

        GameProgress progress = FileIOWorker.loadFromFile(fileToLoad);

        System.out.println("Загружены данные из случайного файла:");
        System.out.println(progress.toString());
    }

}