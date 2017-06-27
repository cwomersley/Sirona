package com.example.chris.strokere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btnWorkout = (Button) findViewById(R.id.btnWorkoutH);
        btnWorkout.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        btnWorkout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Home.this, ExerciseView.class));
            }
        });

        Button btnExercise = (Button) findViewById(R.id.btnExerciseH);
        btnExercise.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        btnWorkout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Home.this, ExerciseList.class));
            }
        });

        Button btnProgress = (Button) findViewById(R.id.btnProgressH);
        btnProgress.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        Button btnPreferences = (Button) findViewById(R.id.btnPreferencesH);
        btnPreferences.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));





    }






}
