package com.example.chris.strokere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.NumberPicker;

public class WorkoutRating extends BaseActivity {

    private NumberPicker ratingScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_rating);

        //for nav bar
        setupNavbar();

        ratingScale = (NumberPicker) findViewById(R.id.ratingScale);


        ratingScale.setMaxValue(20);
        ratingScale.setMinValue(6);
        ratingScale.setWrapSelectorWheel(false);


    }


    //

    @Override
    public int getLayout() {
        return R.layout.activity_home;
    }
}
