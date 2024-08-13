package com.example.taxrobot;

import com.example.taxrobot.taxmanager.util.Options;

import java.lang.reflect.Field;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) throws IllegalAccessException {
        System.out.println(Arrays.toString(Options.class.getDeclaredFields()));
        for (Field field: Options.class.getDeclaredFields()){
            if (field.getName().equals("ZIVILSTAND")){
                String[] arr = (String[]) field.get(null);
                System.out.println(Arrays.toString(arr));
            }
        }
    }
}
