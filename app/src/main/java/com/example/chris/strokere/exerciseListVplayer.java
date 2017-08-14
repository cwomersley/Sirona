package com.example.chris.strokere;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;



public class exerciseListVplayer extends AppCompatActivity {

    ExerciseList exerciselist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exerciselistvplayer);

        TextView tv = (TextView)findViewById(R.id.textView5);
        tv.setText(exerciselist.niceName);

        ImageView img = (ImageView) findViewById(R.id.imageView3);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(exerciseListVplayer.this, ExerciseList.class));
            }
        });

        VideoView video = (VideoView) findViewById(R.id.videoViewNew);
        video.setVideoPath(exerciselist.path);
        video.start();

//Chris's code, call it from his class when all working
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);

            }
        });

    }

}
