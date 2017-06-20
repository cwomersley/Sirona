package com.example.chris.strokere;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.VideoView;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class exerciseView extends AppCompatActivity {

    ArrayList<String> nameList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_view);

        addFiles();

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);


        //asign video to video view and start video

       // vidPath = "android.resource://" + getPackageName() + "/" + R.raw.exercisetest;

        //handler for switching to a different video view (Currently set at 3000, change to variable)
        final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                // code to be run every 30 seconds

                setAndPlayVideo(setStringPath());


                handler.postDelayed(this, 30000);
            }
        };
        //initilize run
        handler.post(run);




    }
   //method for playing video depening on path
    public void setAndPlayVideo(String vidPath) {

        VideoView videoView = (VideoView) findViewById(R.id.videoView);
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

    public String setStringPath(){


        String stringPath = "android.resource://" + getPackageName() + "/" + R.raw.exercisetest;
        

        return stringPath;

    }




    public void  addFiles(){

        Field[] fields = R.raw.class.getFields();
        nameList = new ArrayList<>();

        for (int i = 0; i < fields.length - 1; i++) {
            String name = fields[i].getName();
            nameList.add(name);


        }


    }





    // Menu icons are inflated as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}

