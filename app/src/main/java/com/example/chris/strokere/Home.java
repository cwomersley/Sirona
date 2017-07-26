package com.example.chris.strokere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

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

        Button btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

    }


    //method to log user out
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(Home.this, MainActivity.class));
    }

    //method to open preferneces activity
    public void oPreferences(View view) {
        startActivity(new Intent(Home.this, Preferences.class));
    }



}
