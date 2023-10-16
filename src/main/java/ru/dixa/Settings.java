package ru.dixa;

import java.io.File;
import java.nio.file.Path;

public class Settings {
    public static final Path INSTALL_PATH = Path.of("C:" + File.separator + "GameZz");
    public static final Path SAVEGAME_PATH = INSTALL_PATH.resolve("savegames");

}
