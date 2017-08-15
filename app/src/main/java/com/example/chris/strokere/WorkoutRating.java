package com.example.chris.strokere;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WorkoutRating extends BaseActivity {

    private NumberPicker ratingScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_rating);

        //for nav bar
        setupNavbar();

        ratingScale = (NumberPicker) findViewById(R.id.numberPicker6);
        Button finish = (Button) findViewById(R.id.finishBtn);


        ratingScale.setMaxValue(20);
        ratingScale.setMinValue(6);
        ratingScale.setWrapSelectorWheel(false);

        finish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int borgRating = ratingScale.getValue();


            }
        });

        //pushborg rating to firebase

        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();


            }
        });






    }
    @Override
    public int getLayout () {
        return R.layout.activity_home;
    }

}


