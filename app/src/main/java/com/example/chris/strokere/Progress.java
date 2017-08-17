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
    String dataDay;
    String dataMonth;
    FirebaseUser user;
    DataSnapshot currentSnapshot;

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
                days.clear();
                emptyDays();
                findRatingsData(currentSnapshot);
            }
        });

        Button goBack = (Button) findViewById(R.id.backMonth);
        goBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, -1);
                SimpleDateFormat dateOfMonth = new SimpleDateFormat("MMMM");
                previousMonth = dateOfMonth.format(calendar.getTime());
                checkMonth(previousMonth);
                days.clear();
                emptyDays();
                findRatingsData(currentSnapshot);
            }
        });

        myReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentSnapshot = dataSnapshot;
                findRatingsData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
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

        if(user != null) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                if (snapshot.getKey().equals("BorgRatings")) {
                    String user = snapshot.child(userID).getKey();
                    Log.d("User: ", user);

                    Object object = snapshot.child(userID).getValue();
                    String output = object.toString();
                    Log.d("Initial data: ", output);

                    //Removes the initial '{' so that the data
                    output = output.replace("{", "");

                    //Puts the data into an array
                    String[] splitData = output.split(", ");

                    //Splits the data into day and month pairs
                    for( int i = 0; i < splitData.length; i++)
                    {
                        String part = splitData[i];
                            this.dataDay = part.substring(0, 2);
                            this.dataMonth = part.substring(3, 5);
                            Log.d("Split data: ", dataDay + dataMonth);
                            if(dataMonth.equals(monthNumbers(monthName))) {
                                days.put("d" + dataDay + "P", 2);
                                Log.d("Data month: ", dataMonth);
                            }
                            redrawDays();
                        }

                    }

                }

        }

    }


    private String monthNumbers(String month) {

        Map<String, String> months = new HashMap<String, String>();
        months.put("January", "01");
        months.put("February", "02");
        months.put("March", "03");
        months.put("April", "04");
        months.put("May", "05");
        months.put("June", "06");
        months.put("July", "07");
        months.put("August", "08");
        months.put("September", "09");
        months.put("October", "10");
        months.put("November", "11");
        months.put("December", "12");

        String value = months.get("key");
        String monthNo = months.get(month);

        Log.d("Month no: ", monthNo + monthName);

        return monthNo;

    }


    private void emptyDays() {

        for(int i = 1; i < 10; i++) {
            String index = Integer.toString(i);
            days.put("d" + "0" + index + "P", 1);
        }

        for(int i = 10; i < 32; i++) {
            String index = Integer.toString(i);
            days.put("d" + index + "P", 1);
        }

    }

    public void redrawDays() {


        /*days.put("d01P",randomNum());
        days.put("d02P",randomNum());
        days.put("d03P",randomNum());
        days.put("d04P",randomNum());
        days.put("d05P",randomNum());
        days.put("d06P",randomNum());
        days.put("d07P",randomNum());
        days.put("d08P",randomNum());
        days.put("d09P",randomNum());
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
        days.put("d23P",1);
        days.put("d24P",1);
        days.put("d25P",1);
        days.put("d26P",1);
        days.put("d27P",1);
        days.put("d28P",1);
        days.put("d29P",1);
        days.put("d30P",1);
        days.put("d31P",1); */


        for(Map.Entry<String, Integer> entry : days.entrySet())
        {
            int id = getResources().getIdentifier(entry.getKey(), "id", getPackageName());
            TextView textView = (TextView) findViewById(id);
            if(entry.getValue()==2) {
                textView.setBackground(getResources().getDrawable(R.drawable.calendar_day_done));

            }
            if(entry.getValue()==1) {
                textView.setBackground(getResources().getDrawable(R.drawable.calendar_day));
            }
        }


    }

}

