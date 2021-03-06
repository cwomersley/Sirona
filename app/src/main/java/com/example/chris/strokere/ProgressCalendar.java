package com.example.chris.strokere;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;

/**
 * ProgressCalendar
 *
 * <P>Shows which days the user worked out on and allows them to cycle through each month to see
 * their history
 *
 * @author Sam Ward
 */
public class ProgressCalendar extends BaseActivity {

    private String monthName;
    private String previousMonth;
    private String nextMonth;
    private String thisMonth;
    private Calendar calendar;
    private FirebaseDatabase myFirebaseDatabase;
    private DatabaseReference myReference;
    private String userID;
    private String dataDay;
    private String dataMonth;
    private FirebaseUser user;
    private DataSnapshot currentSnapshot;
    private Integer timesWorkedOut;
    private HashMap<String,Integer> days = new HashMap<String,Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_calendar);
        //Links the navbar buttons to their activities
        setupNavbar();

        myFirebaseDatabase = FirebaseDatabase.getInstance();
        myReference = myFirebaseDatabase.getReference();
        timesWorkedOut = 0;

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

        Button shuffle = (Button) findViewById(R.id.shuffle);
        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisMonth();
            }
        });

        //Gets the current month
        this.calendar = Calendar.getInstance();
        SimpleDateFormat dateOfMonth = new SimpleDateFormat("MMMM");
        this.monthName = dateOfMonth.format(calendar.getTime());

        TextView currentMonth = (TextView) findViewById(R.id.currentMonth);
        currentMonth.setText(this.monthName);
        currentMonth.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        Button goForward = (Button) findViewById(R.id.forwardMonth);
        goForward.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                forwardMonth();
            }
        });

        Button goBack = (Button) findViewById(R.id.backMonth);
        goBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                backMonth();
            }
        });

        monthNoOfDays(this.monthName);
        findRatingsData(currentSnapshot);
        setupProgressText();

    }

    @Override
    public void onResume() {
        super.onResume();

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

        findRatingsData(currentSnapshot);
        setupProgressText();
    }

    /**
     * Checks which month is currently active and shows and hides the days of the month
     * depending on the total number of days in each
     * @param month the currently selected month
     */
    private void monthNoOfDays(String month) {

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

    /**
     * Sets up the view appropriately to the current month at the time the user opens the page
     */
    private void thisMonth() {
        calendar.add(Calendar.MONTH, +1 -1);
        SimpleDateFormat dateOfMonth = new SimpleDateFormat("MMMM");
        thisMonth = dateOfMonth.format(calendar.getTime());
        //Makes the activity show the correct no of days for the month
        monthNoOfDays(thisMonth);
        days.clear();
        emptyDays();
        findRatingsData(currentSnapshot);
        setupProgressText();
    }

    /**
     * Goes forward to the next month sequentially
     */
    private void forwardMonth() {
        calendar.add(Calendar.MONTH, +1);
        SimpleDateFormat dateOfMonth = new SimpleDateFormat("MMMM");
        nextMonth = dateOfMonth.format(calendar.getTime());
        //Makes the activity show the correct no of days for the month
        monthNoOfDays(nextMonth);
        days.clear();
        emptyDays();
        findRatingsData(currentSnapshot);
        setupProgressText();
    }

    /**
     * Goes back to the previous month
     */
    private void backMonth() {
        calendar.add(Calendar.MONTH, -1);
        SimpleDateFormat dateOfMonth = new SimpleDateFormat("MMMM");
        previousMonth = dateOfMonth.format(calendar.getTime());
        monthNoOfDays(previousMonth);
        days.clear();
        emptyDays();
        findRatingsData(currentSnapshot);
        setupProgressText();
    }

    /**
     * Returns this activity (used by the navbar)
     * @return the current activity
     */
    @Override
    public int getLayout() {
        return R.layout.activity_progress_calendar;
    }


    /**
     * Simulates data input from the database. Is not called and is for debug purposes only.
     * @return returns 1 or 2 randomly
     */
    private int randomNum(){
        if(Math.random() < 0.5==true) {
            return 2;
        }
        else {
            return 1;
        }
    }


    private void findRatingsData(DataSnapshot dataSnapshot) {
        //Ensures this method only runs if a user is logged in
        if(user != null) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                if (snapshot.getKey().equals("BorgRatings")) {
                    Object object = snapshot.child(userID).getValue();
                    if(object != null) {
                        String output = object.toString();
                        //Removes the initial '{' so that the data is consistent
                        output = output.replace("{", "");
                        //Puts the data into an array
                        String[] splitData = output.split(", ");
                        //Splits the data into day and month pairs
                        for( int i = 0; i < splitData.length; i++) {
                            String part = splitData[i];
                            this.dataDay = part.substring(0, 2);
                            this.dataMonth = part.substring(3, 5);

                            //Enters each day that corresponds to the active month into the days
                            // HashMap so it can be colored appropriately
                            if (dataMonth.equals(monthNumbers(monthName))) {
                                days.put("d" + dataDay + "P", 2);
                            }

                            redrawDays();
                        }
                    }
                }
            }
        }
    }


    /**
     * Displays the no of times the user has worked out that month
     */
    private void setupProgressText() {
        TextView youWorked = (TextView) findViewById(R.id.youWorked);
        youWorked.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        String workedOut = Integer.toString(timesWorkedOut);
        if(user != null) {
            youWorked.setText("You worked out " + workedOut + " times this month!");
        }
        if(user == null) {
            youWorked.setText("");
        }
    }

    /**
     * Converts the full name of a month into the its sequential number
     * @param month the full name of the month
     * @return
     */
    private String monthNumbers(String month) {
        Map<String, String> months = new HashMap<>();
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
        String monthNo = months.get(month);

        return monthNo;

    }


    /**
     * Empties the days map so days worked out will not carry over from the previously selected
     * month
     */
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

    /**
     * Redraws the calendar according to which days the user has worked out
     */
    private void redrawDays() {
        timesWorkedOut = 0;

        for(Map.Entry<String, Integer> entry : days.entrySet()) {
            int id = getResources().getIdentifier(entry.getKey(), "id", getPackageName());
            TextView textView = (TextView) findViewById(id);

            if(entry.getValue()==2) {
                textView.setBackground(getResources().getDrawable(R.drawable.calendar_day_done));
                timesWorkedOut++;
            }
            if(entry.getValue()==1) {
                textView.setBackground(getResources().getDrawable(R.drawable.calendar_day));
            }
        }
    }

}

