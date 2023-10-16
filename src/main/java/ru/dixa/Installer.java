package ru.dixa;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static ru.dixa.Settings.INSTALL_PATH;

public class Installer {
    private static StringBuilder installLog;
    List<String> pathList;
    String[] paths = {
            "src",
            "res",
            "savegames",
            "temp",
            "src" + File.separator + "main",
            "src" + File.separator + "test",
            "src" + File.separator + "main" + File.separator + "Main.java",
            "src" + File.separator + "main" + File.separator + "Utils.java",
            "res" + File.separator + "drawables",
            "res" + File.separator + "vectors",
            "res" + File.separator + "icons",
            "temp" + File.separator + "temp.txt"
    };

    public Installer(){
        installLog = new StringBuilder("");
        pathList = Arrays.stream(paths).toList();
    }
    public Installer(String[] paths){
        this();

        if (paths != null) {
            installLog = new StringBuilder("");
            pathList = Arrays.stream(paths).toList();
        }
    }
    public void run() {

        for (String path : pathList){
            if (FileIOWorker.createSource(INSTALL_PATH.resolve(path))){
                installLog.append(INSTALL_PATH.resolve(path)).append(" ").append("успешно создан или уже существует.\n");
            } else {
                installLog.append(INSTALL_PATH.resolve(path)).append(" ").append("невозможно создать!\n");
            }
        }

        writeLogToFile(String.valueOf(INSTALL_PATH.resolve("temp" + File.separator + "temp.txt")));
    }

    private void writeLogToFile(String path){

        File logFile = new File(path);

        try {
            FileWriter output = new FileWriter(logFile);
            output.write(String.valueOf(installLog));
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
