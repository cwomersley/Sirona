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
import android.widget.TextView;
import android.widget.VideoView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;


public class ExerciseView extends BaseActivity {

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
    private Integer likeVideo;
    private HashMap<String, Integer> likeHash = new HashMap<>();
    private TextView timerText;
    private int time = 10;
    private Boolean isVidBreak = false;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_view);

        setupNavbar();

        likeBtn = (ImageButton) findViewById(R.id.thumbsUpBtn);
        dissLikeBtn = (ImageButton) findViewById(R.id.thumbsDownbtn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        pause = (ImageButton) findViewById(R.id.pauseResume);
        timerText = (TextView) findViewById(R.id.timerText) ;


        likeBtn.setAlpha(0.5f);
        dissLikeBtn.setAlpha(0.5f);


        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeVideo = 1;
                likeBtn.setAlpha(1f);
                dissLikeBtn.setAlpha(0.5f);
                likeHash.put(nameList.get(0), likeVideo);



            }
        });

        dissLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeVideo = -1;
                dissLikeBtn.setAlpha(1f);
                likeBtn.setAlpha(0.5f);
                likeHash.put(nameList.get(0), likeVideo);

            }
        });


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

        //Method with listener for pause click
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pausevid();

            }
        });

        //make a method


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

        if(nameList.size()>0) {
            String path = nameList.get(0);
            String stringPath = "android.resource://" + getPackageName() + "/" + "/raw/" + path;

            return stringPath;
        }else {
            return null;
        }


    }



    //adds all files in raw to an array and adds file names to an array(currently using to populate database automaticcaly
    // relating to to file names)
    public void  addFiles(){

        Field[] fields = R.raw.class.getFields();

        for (int i = 0; i < fields.length - 1; i++) {

                String name = fields[i].getName();
            if(name != "$change"  && name != "serialVersionUID") {
                nameList.add(name);
            }

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


//Handles the play of exercises videos
    public void timer(long timeLeftMilli) {
        cdt = new CountDownTimer(timeLeftMilli, 100) {
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                i++;
                Log.d("fubar", Long.toString(timeLeft));
                progressBar.setProgress(i);

            }

            public void onFinish() {

                //load the next video and set the time for it ?
                if (nameList.size()> 1) {



                    //test code fbr

                        vidBreak();
                        videoView.setVisibility(View.INVISIBLE);
                        cdt.cancel();
                        isVidBreak = true;
                        pause.setClickable(false);




                }else if (nameList.size() == 1) {

                    videoView.stopPlayback();
                    Intent intent = new Intent(ExerciseView.this, WorkoutRating.class);
                    startActivity(intent);


                }


            }
        };
        cdt.start();
    }


//10 second timer
    public void vidBreak(){

    timerText.setVisibility(View.VISIBLE);

        new CountDownTimer(10000, 900){

        public void onTick(long millisUntilFinished){
            timerText.setText(toTime(time));
            time--;

        }

        public void onFinish(){

            isVidBreak = false;
            nameList.remove(0);
            setAndPlayVideo(setStringPath());
            i = 0;
            timer(10000);
            videoView.setVisibility(View.VISIBLE);
            timerText.setVisibility(View.INVISIBLE);
            time = 10;
            pause.setClickable(true);



        }
        }.start();

        }


    //Checks if number is less than 9 and adds a leading 0
    public String toTime(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }


    public void pauseTimer(){
      cdt.cancel();
      pause.setAlpha(1.0f);
  }

  public void resumeTimer(){
      timer(timeLeft);
      pause.setAlpha(0.0f);

  }

  public String getcurrentExervise(){
      return nameList.get(0);
  }



//Pauses & resumes video and timer + progress bar
  public void pausevid() {
      if (videoView.isPlaying()) {
          videoView.pause();
          pauseTimer();
          Log.d("fubarLeft", Long.toString(timeLeft));

      } else {
          videoView.start();
          resumeTimer();
      }
  }


    @Override
    public int getLayout() {
        return R.layout.activity_home;
    }

}

