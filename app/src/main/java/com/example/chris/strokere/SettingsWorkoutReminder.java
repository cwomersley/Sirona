package com.example.chris.strokere;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.Calendar;

public class SettingsWorkoutReminder extends BaseActivity {

    private TimePicker timePicker;
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;

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

        timePicker = (TimePicker) findViewById(R.id.timePicker);

    }

    public void findDayAndTime() {

        TextView youSelected = (TextView) findViewById(R.id.youSelected);

        int hour = timePicker.getCurrentHour();
        int min = timePicker.getCurrentMinute();

        youSelected.setText("You will be reminded at " + hour + ":" + min);

        Context context = getApplicationContext();
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Home.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        // Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 20, alarmIntent);

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
