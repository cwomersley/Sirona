package com.example.chris.strokere;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * Plays the video selected in the ExerciseList class
 * @author Adam Jessop
 */
public class ExerciseListVplayer extends BaseActivity {

    ExerciseList exerciselist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list_vplayer);
        setupNavbar();

        //Exercise name at the top of the page
        TextView tv = (TextView)findViewById(R.id.textView5);
        tv.setText(exerciselist.niceName);
        tv.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));


        TextView tv2 = (TextView)findViewById(R.id.textView7);
        tv2.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));


        //Image of an exit button
        ImageView img = (ImageView) findViewById(R.id.imageView3);

        //listener for clicking the exit button
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            //when clicked, open the ExerciseList page again
            public void onClick(View v) {
                startActivity(new Intent(ExerciseListVplayer.this, ExerciseList.class));
            }
        });
        //video playing by the path given in ExerciseList
        VideoView video = (VideoView) findViewById(R.id.videoView3);
        video.setVideoPath(exerciselist.path);
        video.start();

        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            //loop the video repeatedly until the user clicks the exit button
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);

            }
        });

    }

    @Override
    public int getLayout() {
        return R.layout.activity_exercise_list_vplayer;
    }

}

