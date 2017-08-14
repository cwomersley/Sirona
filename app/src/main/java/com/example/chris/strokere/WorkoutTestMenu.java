package com.example.chris.strokere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WorkoutTestMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_test_menu);
    }

    public void sitToStands(View view) {
        startActivity(new Intent(WorkoutTestMenu.this, WorkoutTest.class));
    }

    public void shuttleRun(View view) {
        //startActivity(new Intent(WorkoutMenu.this, .class));
    }

    public void stepUps(View view) {
        //startActivity(new Intent(WorkoutMenu.this, .class));
    }
}
