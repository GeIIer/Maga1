package org.example.configuration;

import org.example.exception.PropertyException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadProperties {
    private static final String path = "src/main/resources/config.properties";
    private static volatile Properties properties;
    private ReadProperties() {}

    public static Properties getProperties() {
        try {
            Properties localProperties = properties;
            if (localProperties == null) {
                synchronized (Properties.class) {
                    localProperties = properties;
                    if (localProperties == null) {
                        Properties newProperties = new Properties();
                        newProperties.load(new FileInputStream(path));
                        properties = localProperties = newProperties;
                    }
                }
            }
            return localProperties;
        } catch (FileNotFoundException ex) {
            throw new PropertyException("Файл конфигурации не найден. \n" + ex.getMessage());
        } catch (IOException ex) {
            throw new PropertyException(ex.getMessage());
        }
    }
}
