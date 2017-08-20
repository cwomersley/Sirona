package com.example.chris.strokere;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class SettingsWorkoutReminder extends BaseActivity {

    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_workout_reminder);
        setupNavbar();

        TextView whichDays = (TextView) findViewById(R.id.whichDaysTextWR);
        whichDays.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        Button notifierBtn = (Button) findViewById(R.id.notifierBtn);
        notifierBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                notifier();
                findDayAndTime();
            }
        });

        ToggleButton monWR = (ToggleButton) findViewById(R.id.monWR);

        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);

        int min = timePicker.getCurrentHour();
        int hour = timePicker.getCurrentMinute();



    }

    public void findDayAndTime() {

        TextView youSelected = (TextView) findViewById(R.id.youSelected);

        int min = timePicker.getCurrentHour();
        int hour = timePicker.getCurrentMinute();

        youSelected.setText("You will be reminded at" + hour + ":" + min);




    }

    @Override
    public int getLayout() {
        return R.layout.activity_settings_workout_reminder;
    }

    public void notifier() {

        NotificationReceiver notificationReceiver = new NotificationReceiver();
        Intent intent = new Intent(this, NotificationReceiver.class);
        notificationReceiver.onReceive(this, intent);



    }
}
