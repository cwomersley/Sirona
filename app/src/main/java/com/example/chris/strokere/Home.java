package com.example.chris.strokere;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Home extends BaseActivity {

    Tip tip;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupNavbar();

        Button btnWorkout = (Button) findViewById(R.id.btnWorkoutH);
        btnWorkout.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        btnWorkout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Home.this, WorkoutMenu.class));
            }
        });

        Button btnExercise = (Button) findViewById(R.id.customBtn);
        btnExercise.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        btnExercise.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Home.this, ExerciseList.class));
            }
        });

        Button btnProgress = (Button) findViewById(R.id.selfTestBtn);
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
        TipManager tipManager = new TipManager();

        //Uses the TipManager to get a random tip
        this.tip = tipManager.getATip();
        //Gets the text of the tip
        String tipText = this.tip.getTipText();
        //Gets the activity that the tip should go to
        String tipIntent = this.tip.getTipIntent();

        tipsText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goSomewhere();
            }
        });

        tipsText.setText(tipText);
        //Log.d("Logthis   ",tip);

        tipsText.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));


    }

    private void goSomewhere() {

        Intent intent = new Intent();
        intent.setClassName(Home.this,this.tip.getTipIntent());
        startActivity(intent);

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
