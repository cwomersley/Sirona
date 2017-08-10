package com.example.chris.strokere;

import android.content.Context;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Sam on 10/08/2017.
 */



public class TipsHelper {

    ArrayList<String> tipsList;
    Random randomNumber;

    public TipsHelper () {

        randomNumber = new Random();

        tipsList = new ArrayList();

    }

    private void setupTips() {

        tipsList.add("You can do this and this and this");
        tipsList.add("Also you can do this");
        tipsList.add("To do this you can do this or that");
        tipsList.add("Click on this to do that and this");

    }

    public String getATip()   {

        setupTips();

        try {
            int id = randomNumber.nextInt(tipsList.size());
            String tip = (String) tipsList.get(id);
            return tip;
        }
        catch (Throwable e){
            return null;
        }

    }



}