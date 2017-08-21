package com.example.chris.strokere;

import java.lang.reflect.Field;
import java.util.Arrays;

public class VideoNameChange {

    public String nameChange(String name) {
        name = name.substring(1, name.length());
        return name;
    }

    public String formaliseName(String name) {
        char[] c = name.toCharArray();
        c = Arrays.copyOfRange(c, 1, name.length());
        c[0] = Character.toUpperCase(c[0]);
        name = new String(c);
        name = name.replace("_", " ");
        return name;
    }

    public static String changeName(String name) {
        String a = "";
        Field[] f = R.raw.class.getFields();
        for (Field fields : f) {
            String s = fields.getName();
            if (s.contains(name)) {
                a = s;
            }
        }
        return a;
    }
}
