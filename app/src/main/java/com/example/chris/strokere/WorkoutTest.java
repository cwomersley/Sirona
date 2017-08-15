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

    private ArrayList<String> nameList = new ArrayList<>();
    private DatabaseReference mDatabase;
    private ArrayList<String> exercisesList;
    private ImageButton likeBtn;
    private ImageButton dissLikeBtn;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    private MediaPlayer mp;
    private ImageButton pause;
    private  VideoView videoView;
    private CountDownTimer cdt;
    private int i = 0;
    private long timeLeft;
    private String stringPath;

    String pressedButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_test);
        //Intent in = getIntent();
        //Bundle b = in.getExtras();


        //String s = b.getString("STS");
        //String t = b.getString("SR");


        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        pause = (ImageButton) findViewById(R.id.pauseResume);

        pressedButton = getIntent().getExtras().getString("button");
        switch(pressedButton){
            case "sitToStands":
                stringPath = "android.resource://" + getPackageName() + "/" + "/raw/" + "sit_to_stand";
                break;
            case "shuttleRun":
                stringPath = "android.resource://" + getPackageName() + "/" + "/raw/" + "trunk_rotations";
                break;
        }


        //
        mDatabase = FirebaseDatabase.getInstance().getReference();

        setAndPlayVideo(stringPath);
        //timer(10000);
        //progressBar.setProgress(i);

    }
    //method for playing video depening on path
    public void setAndPlayVideo(String vidPath) {

        videoView = (VideoView) findViewById(R.id.videoViewE);
        videoView.setVideoPath(vidPath);
        videoView.start();
       /** pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(videoView.isPlaying()){
                    videoView.pause();
                    //pauseTimer();

                }else {
                    videoView.start();
                    //resumeTimer();
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
        **/

    }
    //set the path of the video from the arrayList
    //public String setStringPath(){


    //    return stringPath;
    //}

    /**


    // Menu icons are inflated as they were with actionbar

    //public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.menu_main, menu);
        //return true;
    //}


    // use an outer loop to enable the videos to have their own times?


    //timer use to change length an exercise is run for and when the next one should play
    //use new timer for each video ? ? to be able to set the time



    public void timer(long timeLeftMilli) {
        cdt = new CountDownTimer(timeLeftMilli, 100) {
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                i++;
                Log.d("fubar", Integer.toString(i));
                progressBar.setProgress(i);

            }

            public void onFinish() {

                //load the next video and set the time for it ?
                //if (nameList.size()> 1) {

                  //  nameList.remove(0);

                    //setAndPlayVideo(setStringPath());
                    //i = 0;
                    //timer(10000);

                //}else if (nameList.size() == 1) {

                  //  videoView.stopPlayback();


                //}


            //}
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

     **/







}



