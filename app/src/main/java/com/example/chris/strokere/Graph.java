package com.example.chris.strokere;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.HashMap;

import static android.graphics.Color.parseColor;

public class Graph extends BaseActivity {

    FirebaseDatabase myFirebaseDatabase;
    DatabaseReference myReference;
    String userID;
    FirebaseUser user;
    DataSnapshot currentSnapshot;
    String dataDay;
    String dataMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        setupNavbar();


        myFirebaseDatabase = FirebaseDatabase.getInstance();
        myReference = myFirebaseDatabase.getReference();

        HashMap<String, Integer> sts = new HashMap<>();

        sts.put("15_08_17", 9);
        sts.put("17_08_17", 5);
        sts.put("02_08_17", 12);

        //Used documentation in http://www.android-graphview.org/simple-graph/ to construct this



        GraphView graph = (GraphView) findViewById(R.id.graph);


        int[] sco = new int[10];
        sco[0] = 6;
        sco[1] = 5;
        sco[2] = 7;
        sco[3] = 9;

        DataPoint[] scores = new DataPoint[10];
        for (int i=0; i<10; i++) {
            int ni = sco[i];
            DataPoint p = new DataPoint(i, ni);
            scores[i] = p;
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(scores);

        graph.addSeries(series);


        series.setColor(parseColor("#4BAA71"));
        series.setThickness(6);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(8);
        series.setTitle("Weekly Progress");

        GridLabelRenderer gridRenderer = graph.getGridLabelRenderer();

        gridRenderer.setHorizontalLabelsColor(parseColor("#4BAA71"));
        gridRenderer.setVerticalLabelsColor(parseColor("#4BAA71"));
        gridRenderer.setHorizontalAxisTitleColor(parseColor("#4BAA71"));
        gridRenderer.setVerticalAxisTitleColor(parseColor("#4BAA71"));
        gridRenderer.setGridColor(parseColor("#4BAA71"));

        Button btnExercise = (Button) findViewById(R.id.button2);
        btnExercise.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        btnExercise.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findScoresData(currentSnapshot);
            }
        });

        myReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentSnapshot = dataSnapshot;
                findScoresData(dataSnapshot);
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

        findScoresData(currentSnapshot);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_graph;
    }


    private void findScoresData(DataSnapshot dataSnapshot) {

        //Ensures this method only runs if a user is logged in
        if(user != null) {

            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                if (snapshot.getKey().equals("WorkoutTestStats")) {

                    Object object = snapshot.child(userID).child("SitToStands").getValue();

                    String output = object.toString();

                    Log.d("Data0: ", output);


                    //Removes the initial '{' so that the data is consistent
                    output = output.replace("{", "");

                    //Puts the data into an array
                    String[] splitData = output.split(", ");

                    //Splits the data into day and month pairs
                    for( int i = 0; i < splitData.length; i++)
                    {
                        String part = splitData[i];

                        this.dataDay = part.substring(0, 2);

                        Log.d("Data: ", dataDay);

                    }

                }

            }

            Log.d("Data2: ", dataDay);

        }

    }

}
