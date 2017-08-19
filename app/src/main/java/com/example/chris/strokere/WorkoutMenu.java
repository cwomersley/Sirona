package com.example.chris.strokere;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WorkoutMenu extends BaseActivity {

    private Button StandardBtn;
    private Button TestBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_menu);
        setupNavbar();

        StandardBtn = (Button) findViewById(R.id.StandardBtn);
        StandardBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        TestBtn = (Button) findViewById(R.id.shuttleRunBtn);
        TestBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));



    }



    public void standardWorkout(View view) {
        startActivity(new Intent(WorkoutMenu.this, ExerciseView.class));
    }

    public void oTestMenu(View view) {
        startActivity(new Intent(WorkoutMenu.this, WorkoutTestMenu.class));
    }

    @Override
    public int getLayout() {
        return R.layout.activity_workout_menu;
    }

}
