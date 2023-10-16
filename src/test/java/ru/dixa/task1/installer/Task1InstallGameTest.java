package ru.dixa.task1.installer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.dixa.Installer;
import ru.dixa.Settings;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class Task1InstallGameTest {
    String[] paths = {
            "srcTest",
            "resTest",
            "savegamesTest",
            "temp",
            "srcTest" + File.separator + "mainTest",
            "srcTest" + File.separator + "testTest",
            "srcTest" + File.separator + "mainTest" + File.separator + "MainTest.java",
            "srcTest" + File.separator + "mainTest" + File.separator + "UtilsTest.java",
            "resTest" + File.separator + "drawablesTest",
            "resTest" + File.separator + "vectorsTest",
            "resTest" + File.separator + "iconsTest",
            "temp" + File.separator + "temp.txt"
    };

    @BeforeEach
    void setUp() {
        if (Files.exists(Settings.INSTALL_PATH)) {

            try {
                Files.walk(Settings.INSTALL_PATH)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @AfterEach
    void tearDown() {

        if (Files.exists(Settings.INSTALL_PATH)) {

            try {
                Files.walk(Settings.INSTALL_PATH)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    void main() {

        Installer installer = new Installer(paths);
        installer.run();

            for (String path : paths){
                Path convertedPath = Path.of(Settings.INSTALL_PATH + File.separator + Path.of(path));
                boolean fileExist = Files.exists(convertedPath);
                assertTrue(fileExist);
                System.out.println(convertedPath + " успешно создан. - " + fileExist);

            }
            System.out.println();
    }
}