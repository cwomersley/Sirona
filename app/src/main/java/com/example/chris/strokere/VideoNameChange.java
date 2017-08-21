package com.example.chris.strokere;

import java.lang.reflect.Field;
import java.util.Arrays;

public class VideoNameChange {
    /**
     * @param name the videos actual name (with extra letter at beginning for hashmap)
     * @return the new name the user will see
     */
    public String nameChange(String name) {
        name = name.substring(1, name.length());
        return name;
    }

    /**
     * @param name same as above
     * @return this does the same as change, but capitalises first letter
     */
    public static String informalName(String name) {
        char[] c = name.toCharArray();
        c = Arrays.copyOfRange(c, 1, name.length());
        c[0] = Character.toUpperCase(c[0]);
        name = new String(c);
        name = name.replace("_", " ");
        return name;
    }

    /**
     * This method is needed as the name on click misses the first letter so need to find what this was.
     * @param name the name the user sees
     * @return the actual video name
     */
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
