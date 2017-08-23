package com.example.chris.strokere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

/**
 * BaseActivity
 *
 * <P>Extends AppCompatActivity to include navbar methods. Every class that uses the navbar
 * needs to extend this.
 *
 * @author Sam Ward
 */

public abstract class BaseActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        setupNavbar();
    }

    /**
     * Gets which activity is currently active
     */
    protected abstract int getLayout();

    /**
     * Sets up the navbar buttons and ensures that they do not refresh the activity if the user
     * clicks on the one they are currently on
     */
    public void setupNavbar() {

        ImageButton homeNavBtn = (ImageButton) findViewById(R.id.homeNavBtn);
        homeNavBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getLayout() != R.layout.activity_home) {
                    startActivity(new Intent(BaseActivity.this, Home.class));
                }
            }
        });

        ImageButton myExerciseNavBtn = (ImageButton) findViewById(R.id.myExerciseNavBtn);
        myExerciseNavBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getLayout() != R.layout.activity_workout_menu) {
                    startActivity(new Intent(BaseActivity.this, WorkoutMenu.class));
                }
            }
        });

        ImageButton exerciseListNavBtn = (ImageButton) findViewById(R.id.exerciseListNavBtn);
        exerciseListNavBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getLayout() != R.layout.activity_exercise_list) {
                    startActivity(new Intent(BaseActivity.this, ExerciseList.class));
                }
            }
        });

        ImageButton progressNavBtn = (ImageButton) findViewById(R.id.progressNavBtn);
        progressNavBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getLayout() != R.layout.activity_progress_menu) {
                    startActivity(new Intent(BaseActivity.this, ProgressMenu.class));
                }
            }
        });

        ImageButton preferencesNavBtn = (ImageButton) findViewById(R.id.preferencesNavBtn);
        preferencesNavBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getLayout() != R.layout.activity_settings) {
                    startActivity(new Intent(BaseActivity.this, Settings.class));
                }
            }
        });
    }
}
