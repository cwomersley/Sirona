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

    /**
     * Creates the tips that are displayed on the Home activity and adds them to an array
     */
    private void setupTips() {

        Tip tip1 = new Tip("Watch videos of all the exercises by tapping on them in the Exercise List", "Yes", "com.example.chris.strokere.ExerciseList");
        Tip tip2 = new Tip("Logout and change your email and password in Account, under Settings", "Yes", "com.example.chris.strokere.Account");
        Tip tip3 = new Tip("See your progress by tapping on Test Scores in the Progress menu", "Yes", "com.example.chris.strokere.ProgressGraph");

        tipsList.add(tip1);
        tipsList.add(tip2);
        tipsList.add(tip3);


    }

    /**
     * Returns a tip selected at random from an array
     * @return a randomly selected tip
     */
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



