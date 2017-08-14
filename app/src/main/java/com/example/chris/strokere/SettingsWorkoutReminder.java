package com.example.chris.strokere;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

public class SettingsWorkoutReminder extends BaseActivity {

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
            }
        });

        ToggleButton monWR = (ToggleButton) findViewById(R.id.monWR);


           /* public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {

                } */

    }

    @Override
    public int getLayout() {
        return R.layout.activity_settings_workout_reminder;
    }

    public void notifier() {

        NotificationReceiver notificationReceiver = new NotificationReceiver();
        Intent intent = new Intent(this, NotificationReceiver.class);
        notificationReceiver.onReceive(this, intent);

      /*  NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("WorkoutMenu Reminder")
                .setContentText("Time to workout...");

// Sets an ID for the notification
        int mNotificationId = 001;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    } */

    }
}
