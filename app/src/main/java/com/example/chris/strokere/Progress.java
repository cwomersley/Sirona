package com.example.chris.strokere;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Map;
import java.util.HashMap;


public class Progress extends AppCompatActivity {

    public HashMap<String,Integer> days = new HashMap<String,Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);


        TextView mon = (TextView) findViewById(R.id.monP);
        mon.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        TextView tue = (TextView) findViewById(R.id.tueP);
        tue.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        TextView wed = (TextView) findViewById(R.id.wedP);
        wed.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        Button shuffle = (Button) findViewById(R.id.shuffle);

        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redrawDays();
            }
        });

    }

    public int randomNum(){
        if(Math.random() < 0.5==true) {
            return 2;
        }
        else {
            return 1;
        }
    }


    public void redrawDays() {

        days.put("monP",randomNum());
        days.put("tueP",randomNum());
        days.put("wedP",randomNum());
        days.put("thuP",randomNum());
        days.put("friP",randomNum());
        days.put("satP",randomNum());
        days.put("sunP",randomNum());

        for(Map.Entry<String, Integer> entry : days.entrySet())
        {
            int id = getResources().getIdentifier(entry.getKey(), "id", getPackageName());
            TextView textView = (TextView) findViewById(id);
            Log.d("Logthis   ","Key = " + entry.getKey() + ", Value = " + entry.getValue() + id);
            if(entry.getValue()==2) {
                textView.setBackgroundColor(Color.parseColor("#4BAA71"));
            }
            if(entry.getValue()==1) {
                textView.setBackgroundColor(Color.parseColor("#AAAAAA"));
            }
        }



        /*if(days.get("Mon")==true) {
            TextView mon = (TextView) findViewById(R.id.monP);
            mon.setBackgroundColor(Color.BLUE);
        }
        else {
            TextView mon = (TextView) findViewById(R.id.monP);
            mon.setBackgroundColor(Color.parseColor("#FF4081"));
        }*/


    }

}


       // if(days.containsKey(true)) {
        //        mon.setBackgroundColor(Color.RED);
        //        }
