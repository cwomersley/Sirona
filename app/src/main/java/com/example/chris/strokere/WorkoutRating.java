package com.example.chris.strokere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WorkoutRating extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_rating);

        //for nav bar
        setupNavbar();



    }


    //

    @Override
    public int getLayout() {
        return R.layout.activity_home;
    }
}
