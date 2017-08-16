package com.example.chris.strokere;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;


public class WorkoutTest extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    private MediaPlayer mp;
    private ImageButton pause;
    private  VideoView videoView;
    private CountDownTimer cdt;
    private int i = 0;
    private long timeLeft;
    private String vidPath;
    private int clickCount;
    private Button countClicksBtn;
    private long testLength;

    String pressedButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_test);

        clickCount=0;
        countClicksBtn = (Button) findViewById(R.id.countClicksBtn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        pause = (ImageButton) findViewById(R.id.pauseResume);

        //shows different video depending what was clicked on previous activity (WorkoutTestMenu)
        pressedButton = getIntent().getExtras().getString("button");
        switch(pressedButton){
            case "sitToStands":
                vidPath = "android.resource://" + getPackageName() + "/" + "/raw/" + "sit_to_stand";
                testLength=60000;
                countClicksBtn.setVisibility(View.VISIBLE);
                break;
            case "shuttleRun":
                vidPath = "android.resource://" + getPackageName() + "/" + "/raw/" + "trunk_rotations";
                testLength=60000;
                break;
            case "stepUps":
                vidPath = "android.resource://" + getPackageName() + "/" + "/raw/" + "step_ups";
                testLength=60000;
                break;
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        setAndPlayVideo(vidPath);
        timer(testLength);
        progressBar.setProgress(i);

    }

    public void countClicks() {
        clickCount++;
    }

    public void setAndPlayVideo(String vidPath) {

        videoView = (VideoView) findViewById(R.id.videoViewE);
        videoView.setVideoPath(vidPath);
        videoView.start();
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(videoView.isPlaying()){
                    videoView.pause();
                    pauseTimer();

                }else {
                    videoView.start();
                    resumeTimer();
                }

            }
        });

        //loops video playing in video view
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
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
        cdt = new CountDownTimer(testLength, 600) {
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                i++;
                progressBar.setProgress(i);
            }
            public void onFinish() {
                  videoView.stopPlayback();
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



