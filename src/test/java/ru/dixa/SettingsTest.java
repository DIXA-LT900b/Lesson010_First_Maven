package ru.dixa;

import org.junit.jupiter.api.Test;
import ru.dixa.Settings;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SettingsTest {
    @Test
    public void PathNotNullTest() {

        assertNotNull(Settings.INSTALL_PATH);
        assertNotNull(Settings.SAVEGAME_PATH);
    }
}