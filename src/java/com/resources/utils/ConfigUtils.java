package com.resources.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {

    private final static ConfigUtils INSTANCE = new ConfigUtils();

    public static ConfigUtils getInstance() {
        return INSTANCE;
    }

    public String readProperty(String fileName, String key) {
        Properties prop = new Properties();
        InputStream input = null;
        String result = null;
        try {
            File file=new File(fileName);
            input = new FileInputStream(file);

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            result = prop.getProperty(key);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
