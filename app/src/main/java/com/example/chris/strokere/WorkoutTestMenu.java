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
        shuttleRunBtn = (Button) findViewById(R.id.customBtn);
        stepUpsBtn = (Button) findViewById(R.id.Calendar);
        sitToStandsBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        shuttleRunBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        stepUpsBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));


    }

    public void sitToStands(View view) {

        Intent intent = new Intent(getApplicationContext(), WorkoutTest.class);
        intent.putExtra("button", "sitToStands");
        startActivity(intent);
    }

    public void shuttleRun(View view) {
        Intent intent = new Intent(getApplicationContext(), WorkoutTest.class);
        intent.putExtra("button", "shuttleRun");
        startActivity(intent);

    }

    public void stepUps(View view) {
        Intent intent = new Intent(getApplicationContext(), WorkoutTest.class);
        intent.putExtra("button", "stepUps");
        startActivity(intent);
    }
}
