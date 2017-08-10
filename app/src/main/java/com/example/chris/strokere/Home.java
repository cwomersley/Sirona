package com.example.chris.strokere;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupNavbar();

        Button btnWorkout = (Button) findViewById(R.id.btnWorkoutH);
        btnWorkout.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        btnWorkout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Home.this, ExerciseView.class));
            }
        });

        Button btnExercise = (Button) findViewById(R.id.btnExerciseH);
        btnExercise.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        btnExercise.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Home.this, ExerciseList.class));
            }
        });

        Button btnProgress = (Button) findViewById(R.id.btnProgressH);
        btnProgress.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        btnProgress.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Progress.class));
            }
        });

        Button btnPreferences = (Button) findViewById(R.id.btnPreferencesH);
        btnPreferences.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        //Button btnLogout = (Button) findViewById(R.id.btnLogout);
        //btnLogout.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        TextView tipsText = (TextView) findViewById(R.id.tipsText);
        TipsHelper tipsHelper = new TipsHelper();

        String tip = tipsHelper.getATip();

        tipsText.setText(tip);
        //Log.d("Logthis   ",tip);

        tipsText.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));


    }

    @Override
    public int getLayout() {
        return R.layout.activity_home;
    }




    //method to open settings activity
    public void oSettings(View view) {
        startActivity(new Intent(Home.this, Settings.class));
    }



}
