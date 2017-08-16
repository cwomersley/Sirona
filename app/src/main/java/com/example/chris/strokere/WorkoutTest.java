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


public class WorkoutTest extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    private MediaPlayer mp;
    private ImageButton pause;
    private CountDownTimer cdt;
    private int i = 0;
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_test);

        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        pause = (ImageButton) findViewById(R.id.pauseResume);
        videoTest = (VideoView) findViewById(R.id.videoTest);
        howManyNo = (EditText) findViewById(R.id.howManyNo);
        howMany = (TextView) findViewById(R.id.howMany);
        howManyBtn = (Button) findViewById(R.id.howManyBtn);

        //shows different video depending what was clicked on previous activity (WorkoutTestMenu)
        pressedButton = getIntent().getExtras().getString("button");
        switch(pressedButton){
            case "sitToStands":
                workoutTestName="SitToStands";
                vidPath = "android.resource://" + getPackageName() + "/" + "/raw/" + "sit_to_stand";
                testLength=600;
                countDownInterval=6;
                setAndPlayVideo(vidPath);
                timer(testLength);
                progressBar.setProgress(i);
                break;
            case "shuttleRun":
                workoutTestName="ShuttleRun";
                videoTest.setVisibility(View.INVISIBLE);
                testLength=180000;
                countDownInterval=1800;
                timer(testLength);
                progressBar.setProgress(i);
                break;
            case "stepUps":
                workoutTestName="StepUps";
                vidPath = "android.resource://" + getPackageName() + "/" + "/raw/" + "step_ups";
                testLength=60000;
                countDownInterval=600;
                setAndPlayVideo(vidPath);
                timer(testLength);
                progressBar.setProgress(i);
                break;
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();

    }


    //method for button to add user statistics to firebase
    public void howManyConfirm(View view) {
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



public String getTime() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat time = new SimpleDateFormat("dd_MM_YY");
        String timeString = time.format(calendar.getTime());
        Log.d("Time: ", timeString);
        return timeString;


        }

    public void setAndPlayVideo(String vidPath) {
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

        //loops video playing in video view
        videoTest.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                mp.setLooping(true);


            }
        });

    }

    // Menu icons are inflated as they were with actionbar
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void timer(long testLength) {
        cdt = new CountDownTimer(testLength, countDownInterval) {
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                i++;
                progressBar.setProgress(i);
            }
            public void onFinish() {
                videoTest.stopPlayback();
                progressBar.setVisibility(View.INVISIBLE);
                videoTest.setVisibility(View.INVISIBLE);
                howMany.setVisibility(View.VISIBLE);
                howManyBtn.setVisibility(View.VISIBLE);
                howManyNo.setVisibility(View.VISIBLE);
            }
        };
        cdt.start();
    }

    public void pauseTimer(){
        cdt.cancel();
        pause.setAlpha(1.0f);
    }

    public void resumeTimer(){
        timer(timeLeft);
        pause.setAlpha(0.0f);
    }

}



