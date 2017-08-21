package com.example.chris.strokere;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WorkoutMenu extends BaseActivity {

    private Button StandardBtn;
    private Button TestBtn;
    private Button customBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_menu);
        setupNavbar();

        StandardBtn = (Button) findViewById(R.id.StandardBtn);
        StandardBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        customBtn = (Button) findViewById(R.id.customBtn);
        customBtn
                .setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        TestBtn = (Button) findViewById(R.id.selfTestBtn);
        TestBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));




    }



    public void standardWorkout(View view) {
        Intent intent = new Intent(WorkoutMenu.this, ExerciseView.class);
        intent.putExtra("workChoice", "standard");
        startActivity(intent);
    }

    public void oTestMenu(View view) {
        startActivity(new Intent(WorkoutMenu.this, WorkoutTestMenu.class));
    }

    public void customWorkout(View view){
        Intent intent = new Intent(WorkoutMenu.this, ExerciseView.class);
        intent.putExtra("workChoice", "customWorkout");
        startActivity(intent);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_workout_menu;
    }

}
