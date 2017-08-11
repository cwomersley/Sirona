package com.example.chris.strokere;

import android.content.Context;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Sam on 10/08/2017.
 */



public class TipManager {

    HashMap<String,String> tipsMap = new HashMap<>();

    ArrayList<Tip> tipsList;
    Random randomNumber;

    public TipManager() {

        randomNumber = new Random();

        tipsList = new ArrayList();

    }

    private void setupTips() {

        Tip tip1 = new Tip("You can set notifications to remind you when to workout", "Yes", "com.example.chris.strokere.SettingsWorkoutReminder");
        Tip tip2 = new Tip("You can enable and disable voice prompts in Settings", "Yes", "com.example.chris.strokere.Settings");
        Tip tip3 = new Tip("You can see your  or tap the lightbulb", "Yes", "com.example.chris.strokere.Settings");

        tipsList.add(tip1);
        tipsList.add(tip2);


    }

    public Tip getATip()   {

        setupTips();

        try {
            int id = randomNumber.nextInt(tipsList.size());
            Tip tip = tipsList.get(id);
            return tip;
        }
        catch (Throwable e){
            return null;
        }

    }

}



