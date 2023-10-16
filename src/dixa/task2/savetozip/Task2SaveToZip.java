package dixa.task2.savetozip;

import dixa.FileIOWorker;
import dixa.GameProgress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static dixa.Settings.SAVEGAME_PATH;

public class Task2SaveToZip {

    public static void main(String[] args) {

        List<GameProgress> progresses = new ArrayList<>();

        progresses.add(new GameProgress(10,10,10,10));
        progresses.add(new GameProgress(20,20,20,20.5f));
        progresses.add(new GameProgress(30,30,30, 0.05846));

        System.out.println("Подождите, выполняется сохранение...");

        for (GameProgress progress : progresses){
            FileIOWorker.saveToFile(progress);
            try {
                TimeUnit.MILLISECONDS.sleep(1100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Сохранено...");

        FileIOWorker.zipFiles(SAVEGAME_PATH);
        FileIOWorker.deleteFiles(SAVEGAME_PATH);
    }

}