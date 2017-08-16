package com.example.chris.strokere;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;


public class Progress extends BaseActivity {

    String monthName;
    String previousMonth;
    String nextMonth;
    Calendar calendar;
    FirebaseDatabase myFirebaseDatabase;
    DatabaseReference myReference;
    String userID;

    public HashMap<String,Integer> days = new HashMap<String,Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        setupNavbar();

        myFirebaseDatabase = FirebaseDatabase.getInstance();
        myReference = myFirebaseDatabase.getReference();


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
        this.calendar = Calendar.getInstance();
        SimpleDateFormat dateOfMonth = new SimpleDateFormat("MMMM");
        this.monthName = dateOfMonth.format(calendar.getTime());

        TextView currentMonth = (TextView) findViewById(R.id.currentMonth);
        currentMonth.setText(this.monthName);
        currentMonth.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        checkMonth(this.monthName);


        Button goForward = (Button) findViewById(R.id.forwardMonth);
        goForward.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, +1);
                SimpleDateFormat dateOfMonth = new SimpleDateFormat("MMMM");
                nextMonth = dateOfMonth.format(calendar.getTime());
                checkMonth(nextMonth);
            }
        });

        Button goBack = (Button) findViewById(R.id.backMonth);
        goBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, -1);
                SimpleDateFormat dateOfMonth = new SimpleDateFormat("MMMM");
                previousMonth = dateOfMonth.format(calendar.getTime());
                checkMonth(previousMonth);
            }
        });

        myReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                findRatingsData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    userID = user.getUid();
                }
            }
        });

    }


    private void checkMonth (String month) {

        TextView currentMonth = (TextView) findViewById(R.id.currentMonth);
        currentMonth.setText(month);

        this.monthName = month;

        TextView d29P = (TextView) findViewById(R.id.d29P);
        TextView d30P = (TextView) findViewById(R.id.d30P);
        TextView d31P = (TextView) findViewById(R.id.d31P);

        if(monthName.matches("September|April|June|November")) {
            d29P.setVisibility(View.VISIBLE);
            d30P.setVisibility(View.VISIBLE);
            d31P.setVisibility(View.INVISIBLE);
        }

        if(monthName.matches("February")) {
            d29P.setVisibility(View.INVISIBLE);
            d30P.setVisibility(View.INVISIBLE);
            d31P.setVisibility(View.INVISIBLE);
        }

        if(monthName.matches("January|March|May|July|August|October|December")) {
            d29P.setVisibility(View.VISIBLE);
            d30P.setVisibility(View.VISIBLE);
            d31P.setVisibility(View.VISIBLE);
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


    private void findRatingsData(DataSnapshot dataSnapshot) {

        for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
            if(snapshot.getKey().equals("BorgRatings")) {
                String user = snapshot.child(userID).getKey();
                Log.d("Data", user);

                Object object = snapshot.child(userID).getValue();
                String output = object.toString();
                Log.d("Data2", output);

            }

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

