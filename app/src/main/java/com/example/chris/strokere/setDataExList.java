package com.example.chris.strokere;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class setDataExList{

    static HashMap<String, List<String>> map;
    static List<String> list;
    static List<String> allExercises;

    //method for instantiating the header's (parent) names, and the child (exercises) names.
    public static void setData() {

        //instantiates an array list with all the muscle group headers
        list = new ArrayList<>();
        list.add("All Exercises");
        list.add("Arms");
        list.add("Back");
        list.add("Chest");
        list.add("Core");
        list.add("Legs");
        list.add("Shoulders");

        //instantiates a HashMap having each muscle group key mapped with an ArrayList value.
        map = new HashMap<>();
        map.put("All Exercises", new ArrayList<String>());
        map.put("Arms", new ArrayList<String>());
        map.put("Back", new ArrayList<String>());
        map.put("Chest", new ArrayList<String>());
        map.put("Core", new ArrayList<String>());
        map.put("Legs", new ArrayList<String>());
        map.put("Shoulders", new ArrayList<String>());

        allExercises = new ArrayList<>();

        //a for each loop going through all the fields (videos in 'raw')
        Field[] f = R.raw.class.getFields();
        for (Field fields : f)
            try {
                //getName gives the String of each videos name
                String name = fields.getName();
                //makes a character array of the name
                char[] c = name.toCharArray();
                //changes name so its user friendly
                name = VideoNameChange.informalName(name);
                //removes two unwanted pre-set raw files
                if (!name.toLowerCase().equals("change") && !name.toLowerCase().equals("erialVersionuid")) {
                    //add every video All Exercises
                    map.get("All Exercises").add(name);
                    allExercises.add(name);
                    //add to each header depending on first letter in video
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

            } catch (Exception e) {
                Log.d("Exception: ", e.getMessage());
            }
    }
}
