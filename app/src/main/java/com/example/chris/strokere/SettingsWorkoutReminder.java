package com.example.chris.strokere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SettingsWorkoutReminder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_workout_reminder);

        TextView whichDays = (TextView) findViewById(R.id.whichDaysTextWR);
        whichDays.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
    }
}
