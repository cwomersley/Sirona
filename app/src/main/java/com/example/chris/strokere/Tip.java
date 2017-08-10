package com.example.chris.strokere;

/**
 * Created by Sam on 10/08/2017.
 */

public class Tip {

    private String text;
    private String icon;
    private String intent;

        public Tip(String text, String icon, String intent){

            this.text = text;
            this.icon = icon;
            this.intent = intent;

        }

        public String getTipText(){

            return this.text;

        }

        public String getTipIntent(){

            return this.intent;

        }

    }
