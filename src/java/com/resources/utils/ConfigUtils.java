package com.resources.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ConfigUtils {

    private final static ConfigUtils INSTANCE = new ConfigUtils();

    public static ConfigUtils getInstance() {
        return INSTANCE;
    }

    public String getProperty(String fileName, String key) {
        Properties prop = new Properties();
        InputStream input = null;
        String result = null;
        try {
            File file = new File(fileName);
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

    public void setProperty(String fileName, String key, String value) {
        Properties prop = new Properties();
        OutputStream output = null;
        InputStream input = null;
        try {
            input = new FileInputStream(fileName);
            prop.load(input);
            
            output = new FileOutputStream(fileName);

            // set the property
            prop.setProperty(key, value);

            prop.store(output, null);

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
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
