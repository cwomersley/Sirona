package com.example.chris.strokere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WorkoutTestMenu extends AppCompatActivity {

    private Button sitToStandsBtn;
    private Button shuttleRunBtn;
    private Button stepUpsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_test_menu);
        sitToStandsBtn = (Button) findViewById(R.id.sitToStandsBtn);
        shuttleRunBtn = (Button) findViewById(R.id.shuttleRunBtn);
        stepUpsBtn = (Button) findViewById(R.id.stepUpsBtn);

        //Sets standardised font for the buttons on activity_workout_test_menu
        sitToStandsBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        shuttleRunBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        stepUpsBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
    }


    /**
     * Passes String sitToStands on click of button to activity_workout_test
     * and opens this activity.
     * @param view
     */
    public void sitToStands(View view) {
        Intent intent = new Intent(getApplicationContext(), WorkoutTest.class);
        intent.putExtra("button", "sitToStands");
        startActivity(intent);
    }

    /**
     Passes String shuttleRun on click of button to activity_workout_test
     * and opens this activiy.
     * @param view
     */
    public void shuttleRun(View view) {
        Intent intent = new Intent(getApplicationContext(), WorkoutTest.class);
        intent.putExtra("button", "shuttleRun");
        startActivity(intent);

    }

    /**
     Passes String stepUps on click of button to activity_workout_test
     * and opens this activiy.
     * @param view
     */
    public void stepUps(View view) {
        Intent intent = new Intent(getApplicationContext(), WorkoutTest.class);
        intent.putExtra("button", "stepUps");
        startActivity(intent);
    }
}
