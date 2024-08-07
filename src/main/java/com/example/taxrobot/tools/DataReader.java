package com.example.taxrobot.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DataReader {
    public static String readFile(String path){
        if (path.charAt(1) != ':'){
            path = "src/main/resources/data/" + path;
        }

        StringBuilder buffer = new StringBuilder();

        try {
            File file = new File(path);
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                buffer.append(reader.nextLine()).append("\n");
            }

            reader.close();
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return buffer.toString();
    }

    public static Map<String, String> getDataMap(String data){
        Map<String, String> dataMap = new HashMap<>();
        String[] lines = data.split("\n");
        String key, value;


        for (String line: lines){
            String[] splittedLineElements = line.split("=");
            if (splittedLineElements.length != 2) continue;

            key = splittedLineElements[0];
            value = splittedLineElements[1];

            dataMap.put(key, value);
        }

        return dataMap;
    }

    public static Map<String, String> getDataMapFromFile(String path){
        return getDataMap(
                readFile(path)
        );
    }
}
