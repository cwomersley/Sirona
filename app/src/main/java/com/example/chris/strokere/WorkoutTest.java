package com.example.chris.strokere;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Class to display workout tests and write workout tests stats to FIrebase database
 * Information corresponding to the particular test is passed through from WorkoutTest class
 */
public class WorkoutTest extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private MediaPlayer mp;
    private ImageButton pause;
    private CountDownTimer cdt;
    private long timeLeft;
    private String vidPath;
    private long testLength;
    private long countDownInterval;
    private VideoView videoTest;
    private String pressedButton;
    private EditText howManyNo;
    private TextView howMany;
    private Button howManyBtn;
    private String workoutTestName;
    private TextView testTimer;
    private int time;
    private TextView testText;
    private Button proceedBtn;
    private Button shuttleStartBtn;
    private ImageView shuttleImage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_test);

        shuttleStartBtn= (Button) findViewById(R.id.shuttleStartBtn);
        testText= (TextView) findViewById(R.id.testText);
        pause = (ImageButton) findViewById(R.id.pauseResume);
        videoTest = (VideoView) findViewById(R.id.videoTest);
        howManyNo = (EditText) findViewById(R.id.howManyNo);
        howMany = (TextView) findViewById(R.id.howMany);
        proceedBtn = (Button) findViewById(R.id.proceedBtn);
        howManyBtn = (Button) findViewById(R.id.howManyBtn);
        testTimer = (TextView) findViewById(R.id.testTimer);
        shuttleImage= (ImageView) findViewById(R.id.shuttleImage);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Sets standardised font for each item on activity_workout
        shuttleStartBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        testText.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        howManyNo.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        howMany.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        proceedBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        howManyBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));


        /**
         * sets the path for exercise test videos depending on which button was pressed on the previous activity (activity_workout_test_menu)
         * plays a short video showing a demonstation of the exercise test or displays an image
         */
        pressedButton = getIntent().getExtras().getString("button");
        switch(pressedButton){
            case "sitToStands":
                workoutTestName="SitToStands";
                testText.setText("See how many sit to stands you can do in a minute!");
                vidPath = "android.resource://" + getPackageName() + "/" + "/raw/" + "esit_to_stand";
                testLength=60000;
                time=60;
                videoTest.setVideoPath(vidPath);
                videoTest.start();
                videoTest.stopPlayback();
                break;
            case "shuttleRun":
                workoutTestName="ShuttleRun";
                testText.setText("See how many shuttle runs you can do in 3 minutes!");
                shuttleImage.setVisibility(View.VISIBLE);
                proceedBtn.setVisibility(View.INVISIBLE);
                shuttleStartBtn.setVisibility(View.VISIBLE);
                videoTest.setVisibility(View.INVISIBLE);
                testLength=180000;
                time=180;
                break;
            case "stepUps":
                workoutTestName="StepUps";
                testText.setText("See how many step ups you can do in a minute!");
                vidPath = "android.resource://" + getPackageName() + "/" + "/raw/" + "estep_ups";
                testLength=60000;
                time=60;
                videoTest.setVideoPath(vidPath);
                videoTest.start();
                videoTest.stopPlayback();
                break;
        }
    }

    /**
     * Starts shuttle run exercise test
     * @param view
     */
    public void shuttleStart (View view) {
        testText.setVisibility(View.INVISIBLE);
        shuttleStartBtn.setVisibility(View.INVISIBLE);
        runTimer(testLength);
    }

    /**
     * Starts step ups or sit to stands exercise test and video
     * @param view
     */
    public void testProceed (View view) {
        testText.setVisibility(View.INVISIBLE);
        proceedBtn.setVisibility(View.INVISIBLE);
        setAndPlayVideo(vidPath);
        runTimer(testLength);
    }




    /**
     * adds exercise test statistics to Firebase on button click
     * @param view
     */
    public void testConfirm(View view)
    {
        if (!howManyNo.getText().toString().equals("")) {
            final int noOfReps=Integer.parseInt(howManyNo.getText().toString());
            FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        mDatabase.child("WorkoutTestStats").child(user.getUid()).child(workoutTestName).child(getTime()).setValue(noOfReps);
                    }
                }
            });
            Toast.makeText(this, "Well done!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(WorkoutTest.this, WorkoutTestMenu.class));
            finish();
        }
        else {
            Toast.makeText(this, "Please enter the number of reps you have done.", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Gets current date for use with exercise test statistics
     * @return
     */
    public String getTime()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat time = new SimpleDateFormat("dd_MM_YY");
        String timeString = time.format(calendar.getTime());
        Log.d("Time: ", timeString);
        return timeString;
    }

    /**
     * Sets the video to a path
     * @param vidPath
     */
    public void setAndPlayVideo(String vidPath)
    {
        videoTest.setVideoPath(vidPath);
        videoTest.start();
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(videoTest.isPlaying()){
                    videoTest.pause();
                    pauseTimer();

                }else {
                    videoTest.start();
                    resumeTimer();
                }

            }
        });

        //Loops the video
        videoTest.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);

            }
        });

    }

    /**
     * Pauses the countdown timer in the exercise test
     */
    public void pauseTimer(){
        cdt.cancel();
        pause.setAlpha(1.0f);
    }

    /**
     * Resumes the timer in the exercise test
     */
    public void resumeTimer(){
        runTimer(timeLeft);
        pause.setAlpha(0.0f);
    }

    /**
     * Adds a zero to the countdown timer when it drops below 10
     * @param number
     * @return
     */
    public String toTime(int number)
    {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    /**
     * Creates a countdown timer to to countdown alongside the exercise tests
     * @param testLength
     */
    public void runTimer(long testLength)
    {
        testTimer.setVisibility(View.VISIBLE);
        cdt = new CountDownTimer(testLength, testLength/100){
            public void onTick(long millisUntilFinished){
                timeLeft = millisUntilFinished;
                time=(int)timeLeft/1000;
                testTimer.setText(toTime(time));
                time--;

            }
            public void onFinish(){
                videoTest.stopPlayback();
                videoTest.setVisibility(View.INVISIBLE);
                howMany.setVisibility(View.VISIBLE);
                howManyBtn.setVisibility(View.VISIBLE);
                howManyNo.setVisibility(View.VISIBLE);
                testTimer.setVisibility(View.INVISIBLE);
                cdt.cancel();

            }
        };
        cdt.start();
    }
}



