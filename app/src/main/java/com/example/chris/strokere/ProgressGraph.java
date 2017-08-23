package com.example.chris.strokere;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
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
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.graphics.Color.parseColor;

public class ProgressGraph extends BaseActivity {

    private FirebaseDatabase myFirebaseDatabase;
    private DatabaseReference myReference;
    private String userID;
    private FirebaseUser user;
    private DataSnapshot currentSnapshot;
    private String dataDay;
    private String dataMonth;
    private int[] sco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_graph);
        setupNavbar();
        setupTestName("Sit To Stands");
        myFirebaseDatabase = FirebaseDatabase.getInstance();
        myReference = myFirebaseDatabase.getReference();
        this.sco = new int[20];

        Button sitToStandsBtn = (Button) findViewById(R.id.sitToStandsBtn);
        sitToStandsBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        sitToStandsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Arrays.fill(sco, 0);
                findScoresData(currentSnapshot, "SitToStands");
                setupTestName("Sit To Stands");
            }
        });
        Button stepUpsBtn = (Button) findViewById(R.id.StepUpsBtn);
        stepUpsBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        stepUpsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Arrays.fill(sco, 0);
                findScoresData(currentSnapshot, "StepUps");
                setupTestName("Step Ups");
            }
        });

        Button shuttleRun = (Button) findViewById(R.id.ShuttleRunBtn);
        shuttleRun.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        shuttleRun.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Arrays.fill(sco, 0);
                findScoresData(currentSnapshot, "ShuttleRun");
                setupTestName("Shuttle Run");
            }
        });

        //Connects to the Firebase database
        myReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentSnapshot = dataSnapshot;
                findScoresData(dataSnapshot, "SitToStands");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    userID = user.getUid();
                }
            }
        });

        findScoresData(currentSnapshot, "SitToStands");
        setupGraph();
    }

    @Override
    public void onResume() {
        super.onResume();

        myReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentSnapshot = dataSnapshot;
                findScoresData(currentSnapshot, "SitToStands");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        findScoresData(currentSnapshot, "SitToStands");
    }

    /**
     * Returns this activity (used by the navbar)
     * @return the current activity
     */
    @Override
    public int getLayout() {
        return R.layout.activity_progress_graph;
    }

    /**
     * Gets the appropriate data from Firebase
     * @param dataSnapshot the data from Firebase
     * @param testType which test to look for
     */
    private void findScoresData(DataSnapshot dataSnapshot, String testType) {

        //Ensures this method only runs if a user is logged in
        if (user != null) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                if (snapshot.getKey().equals("WorkoutTestStats")) {
                    Object object = snapshot.child(userID).child(testType).getValue();
                    if(object != null) {
                        String output = object.toString();
                        Log.d("Data0: ", output);
                        //Removes the curly brackets so that the data is consistent
                        output = output.replace("{", ",");
                        output = output.replace("}", ",");
                        //Puts the data into an array
                        String[] splitData = output.split(" ");
                        //Splits the data into day and month pairs
                        for (int i = 0; i < splitData.length; i++) {
                            String part = splitData[i];

                            Pattern pattern = Pattern.compile("\\=(.*?)\\,");
                            Matcher matcher = pattern.matcher(part);
                            while (matcher.find()) {
                                dataDay = matcher.group(1);
                                Log.d("Data3: ", part + "changed to: " + dataDay);

                            }
                            int dataInt = Integer.parseInt(dataDay);
                            sco[i] = dataInt;
                        }
                        setupGraph();
                    }
                }
            }
        }
    }

    /**
     * Changes the title of the graph to display the relevant test name
     * @param name the name of the test to be displayed
     */
    private void setupTestName(String name) {

        TextView testName = (TextView) findViewById(R.id.testName);
        testName.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        testName.setText(name);
    }

    /**
     * Creates the graph view using data from Firebase
     */
    private void setupGraph() {

        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.removeAllSeries();
        DataPoint[] scores = new DataPoint[20];
        for (int i = 0; i < 20; i++) {
            int ni = sco[i];
            DataPoint p = new DataPoint(i, ni);
            scores[i] = p;
        }

        //Referred to documentation in http://www.android-graphview.org/simple-graph/ to construct
        // the LineGraphSeries and GridLabelRenderer and set their attributes
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(scores);
        graph.addSeries(series);
        series.setColor(parseColor("#4BAA71"));
        series.setThickness(6);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(8);
        series.setTitle("Weekly Progress Calendar");

        GridLabelRenderer gridRenderer = graph.getGridLabelRenderer();
        gridRenderer.setHorizontalLabelsColor(parseColor("#4BAA71"));
        gridRenderer.setVerticalLabelsColor(parseColor("#4BAA71"));
        gridRenderer.setHorizontalAxisTitleColor(parseColor("#4BAA71"));
        gridRenderer.setVerticalAxisTitleColor(parseColor("#4BAA71"));
        gridRenderer.setGridColor(parseColor("#4BAA71"));
        gridRenderer.setVerticalAxisTitle("Test Score");
        gridRenderer.setVerticalAxisTitleColor(parseColor("#4BAA71"));
        gridRenderer.setHorizontalAxisTitle("Workout No.");
        gridRenderer.setHorizontalAxisTitleColor(parseColor("#4BAA71"));

    }

}
