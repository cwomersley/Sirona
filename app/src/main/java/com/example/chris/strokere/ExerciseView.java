package com.example.chris.strokere;

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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;


public class ExerciseView extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_view);

        likeBtn = (ImageButton) findViewById(R.id.thumbsUpBtn);
        dissLikeBtn = (ImageButton) findViewById(R.id.thumbsDownbtn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        pause = (ImageButton) findViewById(R.id.pauseResume);

        //
        mDatabase = FirebaseDatabase.getInstance().getReference();



        addFiles();

        //remove later - used to add to db
        //populateDB();

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        setAndPlayVideo(setStringPath());
        timer(10000);
        progressBar.setProgress(i);






    }
   //method for playing video depening on path
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
    //set the path of the video from the arrayList
    public String setStringPath(){

        if(nameList.size() > 0) {
            String path = nameList.get(1);
            String stringPath = "android.resource://" + getPackageName() + "/" + "/raw/" + path;
            return stringPath;
        }else{


            return null;
        }


    }



    //adds all files in raw to an array and adds file names to an array(currently using to populate database automaticcaly
    // relating to to file names)
    public void  addFiles(){

        Field[] fields = R.raw.class.getFields();

        for (int i = 0; i < fields.length - 1; i++) {
            String name = fields[i].getName();
            //add if statement if they match the name of a requested file from server ????
           nameList.add(name);
      }
    }

    //populate the database from the list of files (to be used to auto populate db )
    public void populateDB(){

        Field[] fields = R.raw.class.getFields();
        exercisesList = new ArrayList<>();


        for (int i = 0; i < fields.length - 1; i++) {
            String name = fields[i].getName();
            exercisesList.add(name);

        }

        //removes the first $change value from the array
        exercisesList.remove(0);

        for (int i = 0; i < exercisesList.size(); i++) {
            String name = exercisesList.get(i);


            mDatabase.child("exercises").push().setValue(name);


        }



    }




    // Menu icons are inflated as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    // use an outer loop to enable the videos to have their own times?


    //timer use to change length an exercise is run for and when the next one should play
    //use new timer for each video ? ? to be able to set the time



    public void timer(long timeLeftMilli) {
        cdt = new CountDownTimer(timeLeftMilli, 100) {
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                i++;
                progressBar.setProgress(i*100/(10000/100));
            }

            public void onFinish() {

                //load the next video and set the time for it ?

                nameList.remove(1);
                setAndPlayVideo(setStringPath());
                timer(10000);
                i = 0;


            }
        };
        cdt.start();
    }

  public void pauseTimer(){
      cdt.cancel();
      pause.setImageAlpha(1);
  }

  public void resumeTimer(){
      timer(timeLeft);
      pause.setImageAlpha(0);
  }


    public void likeExercise(){


    }

    public void disslikeExercise(){


    }




}

