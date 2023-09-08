package org.prizrakk.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private Properties properties;
    private File configFile;

    public ConfigManager() {
        properties = new Properties();
        configFile = new File("config.properties");

        // Загрузка конфигурационного файла, если существует
        if (configFile.exists()) {
            try (FileInputStream inputStream = new FileInputStream(configFile)) {
                properties.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public void saveConfig() {
        try (FileOutputStream outputStream = new FileOutputStream(configFile)) {
            properties.store(outputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

