package com.example.chris.strokere;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;


public class Progress extends BaseActivity {

    public HashMap<String,Integer> days = new HashMap<String,Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        setupNavbar();

        TextView mon = (TextView) findViewById(R.id.d1P);
        mon.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        TextView tue = (TextView) findViewById(R.id.d2P);
        tue.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        TextView wed = (TextView) findViewById(R.id.d3P);
        wed.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        Button shuffle = (Button) findViewById(R.id.shuffle);

        Button goGraph = (Button) findViewById(R.id.goGraph);
        goGraph.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Progress.this, Graph.class));
            }
        });

        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redrawDays();
            }
        });

        //Gets the current month
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateOfMonth = new SimpleDateFormat("MMMM");
        String nameOfMonth = dateOfMonth.format(calendar.getTime());


        TextView currentMonth = (TextView) findViewById(R.id.currentMonth);
        currentMonth.setText(nameOfMonth);
        currentMonth.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        TextView d29P = (TextView) findViewById(R.id.d29P);
        TextView d30P = (TextView) findViewById(R.id.d30P);
        TextView d31P = (TextView) findViewById(R.id.d31P);

        if(nameOfMonth.matches("September|April|June|November")) {
            d31P.setVisibility(View.INVISIBLE);
        }

        if(nameOfMonth.matches("Februrary")) {
            d29P.setVisibility(View.INVISIBLE);
            d30P.setVisibility(View.INVISIBLE);
            d31P.setVisibility(View.INVISIBLE);
        }

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
        days.put("d1P",randomNum());
        days.put("d2P",randomNum());
        days.put("d3P",randomNum());
        days.put("d4P",randomNum());
        days.put("d5P",randomNum());
        days.put("d6P",randomNum());
        days.put("d7P",randomNum());
        days.put("d8P",randomNum());
        days.put("d9P",randomNum());
        days.put("d10P",randomNum());
        days.put("d11P",randomNum());
        days.put("d12P",randomNum());
        days.put("d13P",randomNum());
        days.put("d14P",randomNum());
        days.put("d15P",randomNum());
        days.put("d16P",randomNum());
        days.put("d17P",randomNum());
        days.put("d18P",randomNum());
        days.put("d19P",randomNum());
        days.put("d20P",randomNum());
        days.put("d21P",randomNum());
        days.put("d22P",randomNum());
        days.put("d23P",randomNum());
        days.put("d24P",randomNum());
        days.put("d25P",randomNum());
        days.put("d26P",randomNum());
        days.put("d27P",randomNum());
        days.put("d28P",randomNum());


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
