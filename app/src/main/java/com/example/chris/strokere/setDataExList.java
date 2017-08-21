package com.example.chris.strokere;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class setDataExList{

    static HashMap<String, List<String>> map;
    static List<String> list;
    static List<String> allExercises;

    public static void setData() {
        //below instantiates the headers, and then makes a map of headers to arraylists
        list = new ArrayList<>();
        allExercises = new ArrayList<>();
        map = new HashMap<>();
        list.add("All Exercises");
        list.add("Arms");
        list.add("Back");
        list.add("Chest");
        list.add("Core");
        list.add("Legs");
        list.add("Shoulders");
        map.put("All Exercises", new ArrayList<String>());
        map.put("Arms", new ArrayList<String>());
        map.put("Back", new ArrayList<String>());
        map.put("Chest", new ArrayList<String>());
        map.put("Core", new ArrayList<String>());
        map.put("Legs", new ArrayList<String>());
        map.put("Shoulders", new ArrayList<String>());

        //below then uses a for loop to go through each exercise video and maps them accordingly.
        Field[] f = R.raw.class.getFields();
        for (Field fields : f)
            try {
                String name = fields.getName();
                char[] c = name.toCharArray();
                char[] d = Arrays.copyOfRange(c, 1, name.length());
                d[0] = Character.toUpperCase(d[0]);
                name = new String(d); //this is what you changed
                name = name.replace("_", " ");
                if (!name.toLowerCase().equals("change") && !name.toLowerCase().equals("erialVersionuid")) {
                    map.get("All Exercises").add(name);
                    allExercises.add(name);
                    switch (c[0]) {
                        case 'a':
                            map.get("Arms").add(name);
                            break;
                        case 'b':
                            map.get("Back").add(name);
                            break;
                        case 'c':
                            map.get("Chest").add(name);
                            break;
                        case 'd':
                            map.get("Core").add(name);
                            break;
                        case 'e':
                            map.get("Legs").add(name);
                            break;
                        case 'f':
                            map.get("Shoulders").add(name);
                            break;
                    }
                }

            } catch (IllegalArgumentException e) {
            }
    }
}
