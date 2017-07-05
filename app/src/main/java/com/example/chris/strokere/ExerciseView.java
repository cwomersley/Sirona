package com.example.chris.strokere;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.VideoView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;


public class ExerciseView extends AppCompatActivity {

    ArrayList<String> nameList;
    private DatabaseReference mDatabase;
    ArrayList<String> exercisesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_view);

        //
        mDatabase = FirebaseDatabase.getInstance().getReference();



        addFiles();
        populateDB();

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        setAndPlayVideo(setStringPath());
        timer();




    }
   //method for playing video depening on path
    public void setAndPlayVideo(String vidPath) {

        VideoView videoView = (VideoView) findViewById(R.id.videoViewE);
        videoView.setVideoPath(vidPath);
        videoView.start();

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

        String path = nameList.get(1);


        String stringPath = "android.resource://" + getPackageName() + "/" + "/raw/" + path;


        return stringPath;

    }



    //adds all files in raw to an array and adds file names to an array
    public void  addFiles(){

        Field[] fields = R.raw.class.getFields();
        nameList = new ArrayList<>();

        for (int i = 0; i < fields.length - 1; i++) {
            String name = fields[i].getName();
            //add if statment if they match the name of a requested file from server ????
           nameList.add(name);
      }
    }

    //populate the database from the list of files
    public void populateDB(){

        Field[] fields = R.raw.class.getFields();
        exercisesList = new ArrayList<>();


        for (int i = 0; i < fields.length - 1; i++) {
            String name = fields[i].getName();
            exercisesList.add(name);

        }

        //removes the first $change(remove) value from the array
        exercisesList.remove(0);

        for (int i = 0; i < exercisesList.size(); i++) {
            String name = exercisesList.get(i);


            mDatabase.child("exercises").push().setValue(name);
            finish();

        }



    }





    // Menu icons are inflated as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    //timer use to change length an exercise is run for and when the next one should play
    public void timer() {
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                //insert code to update progess bar ?
            }

            public void onFinish() {

                //load the next video and set the time for it ?

                nameList.remove(1);
                setAndPlayVideo(setStringPath());
            }
        }.start();
    }






}

