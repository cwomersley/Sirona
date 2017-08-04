package com.example.chris.strokere;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;
import java.util.HashMap;


public class Progress extends BaseActivity {

    public HashMap<String,Integer> days = new HashMap<String,Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        setupNavbar();

        TextView mon = (TextView) findViewById(R.id.mon1P);
        mon.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        TextView tue = (TextView) findViewById(R.id.tue1P);
        tue.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        TextView wed = (TextView) findViewById(R.id.wed1P);
        wed.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        Button shuffle = (Button) findViewById(R.id.shuffle);

        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redrawDays();
            }
        });

    }

    @Override
    public int getLayout() {
        return R.layout.activity_progress;
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

        //This section models input from the database with a random number instead
        days.put("mon1P",randomNum());
        days.put("mon2P",randomNum());
        days.put("mon3P",randomNum());
        days.put("mon4P",randomNum());
        days.put("tue1P",randomNum());
        days.put("tue2P",randomNum());
        days.put("tue3P",randomNum());
        days.put("tue4P",randomNum());
        days.put("wed1P",randomNum());
        days.put("wed2P",randomNum());
        days.put("wed3P",randomNum());
        days.put("wed4P",randomNum());
        days.put("thu1P",randomNum());
        days.put("thu2P",randomNum());
        days.put("thu3P",randomNum());
        days.put("thu4P",randomNum());
        days.put("fri1P",randomNum());
        days.put("fri2P",randomNum());
        days.put("fri3P",randomNum());
        days.put("fri4P",randomNum());
        days.put("sat1P",randomNum());
        days.put("sat2P",randomNum());
        days.put("sat3P",randomNum());
        days.put("sat4P",randomNum());
        days.put("sun1P",randomNum());
        days.put("sun2P",randomNum());
        days.put("sun3P",randomNum());
        days.put("sun4P",randomNum());


        for(Map.Entry<String, Integer> entry : days.entrySet())
        {
            int id = getResources().getIdentifier(entry.getKey(), "id", getPackageName());
            TextView textView = (TextView) findViewById(id);
            Log.d("Logthis   ","Key = " + entry.getKey() + ", Value = " + entry.getValue() + id);
            if(entry.getValue()==2) {
                textView.setBackground(getResources().getDrawable(R.drawable.calendar_day_done));
                //textView.setBackgroundColor(Color.parseColor("#4BAA71"));

            }
            if(entry.getValue()==1) {
                textView.setBackground(getResources().getDrawable(R.drawable.calendar_day));
                //textView.setBackgroundColor(Color.parseColor("#BABABA"));
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
