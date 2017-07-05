package com.example.chris.strokere;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;

public class Progress extends AppCompatActivity {

    public HashMap<String,Boolean> days = new HashMap<String,Boolean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        days.put("Mon",false);
        days.put("Tue",false);
        days.put("Wed",false);


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

    public boolean randomBoolean(){
        return Math.random() < 0.5;
    }

    public void redrawDays() {

        days.put("Mon",randomBoolean());
        days.put("Tue",false);
        days.put("Wed",false);
        days.put("Thu",false);
        days.put("Fri",false);
        days.put("Sat",false);
        days.put("Sun",false);

        if(days.get("Mon")==true) {
            TextView mon = (TextView) findViewById(R.id.monP);
            mon.setBackgroundColor(Color.BLUE);
        }
        else {
            TextView mon = (TextView) findViewById(R.id.monP);
            mon.setBackgroundColor(Color.parseColor("#FF4081"));
        }

    }

}


       // if(days.containsKey(true)) {
        //        mon.setBackgroundColor(Color.RED);
        //        }
