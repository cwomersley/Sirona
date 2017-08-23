package com.example.chris.strokere;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
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
    private VideoView videoView;
    private CountDownTimer cdt;
    private int i = 0;
    private long timeLeft;
    private Integer likeVideo;
    private static HashMap<String, Integer> likeHash = new HashMap<>();
    private TextView timerText;
    private int time = 10;
    private Boolean isVidBreak = false;
    private TextView exerciseNameText;
    private CountDownTimer vidBreaktmr;
    private Long vidBreakTimeLeft;
    private VideoStats videoStats;
    private WorkoutMenu workoutMenu;
    private ArrayList<String> customWorkout = new ArrayList<>();
    private String pressedButton;
    private String[] exercises;
    private String output;
    private ArrayList<String> warmUpList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_view);
        //setupNavbar();
        videoStats = new VideoStats();

        likeBtn = (ImageButton) findViewById(R.id.thumbsUpBtn);
        dissLikeBtn = (ImageButton) findViewById(R.id.thumbsDownbtn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        pause = (ImageButton) findViewById(R.id.pauseResume);
        timerText = (TextView) findViewById(R.id.timerText);
        exerciseNameText = (TextView) findViewById(R.id.exerciseNameText);
        pressedButton = getIntent().getExtras().getString("workChoice");
        workoutMenu = new WorkoutMenu();
        output = workoutMenu.getOutput();

        likeBtn.setAlpha(0.5f);
        dissLikeBtn.setAlpha(0.5f);


        //
        mDatabase = FirebaseDatabase.getInstance().getReference();


        //remove later - used to add to db
        //populateDB();

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);


        if (pressedButton.equals("customWorkout")) {
            customWorkout.clear();
            addFiles();
            printarray();
            setAndPlayVideo(setStringPath());
            timer(60000);
            progressBar.setProgress(i);



        } else if
                (pressedButton.equals("standard")) {
            addFiles();
            setAndPlayVideo(setStringPath());
            timer(60000);
            progressBar.setProgress(i);
        }


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


    }


    /*
    *Sets the
    *@param vidPath This is the path of the video to be played
    *@return nothing
    *
    * */
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

        //loops video playing in video view
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

    }


    /*
    * @return the complete video path that is useable by videoView
     */
    public String setStringPath() {

        if (nameList.size() > 0) {

            String path = nameList.get(0);
            String stringPath = "android.resource://" + getPackageName() + "/" + "/raw/" + path;

            return stringPath;
        } else {
            return null;
        }


    }


    //adds all files in raw to an array and adds file names to an array(currently using to populate database automaticcaly
    // relating to to file names)
    public void addFiles() {

        Field[] fields = R.raw.class.getFields();



        for (int i = 0; i < fields.length - 1; i++) {

            String name = fields[i].getName();
            if (name != "$change" && name != "serialVersionUID") {
                nameList.add(name);
            }
            Collections.shuffle(nameList);




        }
        setWarmUpList();

        for(String s: warmUpList) {
            int x = 0;
            nameList.add(x, s);
            x++;
        }


        for(int z = 10;  z <21 ; z++){
            nameList.remove(z);
        }
        Log.d("edstring", nameList.toString());
    }

    //populate the database from the list of files (to be used to auto populate db )
    public void populateDB() {

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


            mDatabase.child("Exercises").child("Name").child(Integer.toString(i)).setValue(name);

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


        exerciseNameText.setText(nameClean(nameList.get(0)));

        cdt = new CountDownTimer(timeLeftMilli, 600) {
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                i++;
                Log.d("fubar", Long.toString(timeLeft));
                progressBar.setProgress(i);

            }

            public void onFinish() {

                //load the next video and set the time for it ?
                if (nameList.size() > 1) {

                    resetLikebt();

                    //test code fbr
                    videoView.setVisibility(View.INVISIBLE);
                    vidBreak(10000l);
                    cdt.cancel();
                    isVidBreak = true;
                    pause.setClickable(false);
                    exerciseNameText.setText("Next: " + nameClean(nameList.get(1)));
                    setEnabledBtn();
                    Log.d("kindle", nameList.toString());


                } else if (nameList.size() == 1) {

                    videoView.stopPlayback();
                    Intent intent = new Intent(ExerciseView.this, WorkoutRating.class);
                    startActivity(intent);
                    finish();


                }


            }
        };
        cdt.start();
    }


    //10 second timer
    public void vidBreak(Long breaktimeLeft) {

        timerText.setVisibility(View.VISIBLE);

        vidBreaktmr = new CountDownTimer(breaktimeLeft, 990) {

            public void onTick(long millisUntilFinished) {
                vidBreakTimeLeft = millisUntilFinished;
                timerText.setText(toTime(time));
                time--;

            }

            public void onFinish() {

                isVidBreak = false;
                nameList.remove(0);
                Log.d("ywhy", nameList.toString());
                setAndPlayVideo(setStringPath());
                i = 0;

                timer(60000);
                videoView.setVisibility(View.VISIBLE);

                timerText.setVisibility(View.INVISIBLE);

                time = 10;
                pause.setClickable(true);
                setEnabledBtn();


            }
        }.start();

    }


    //Checks if number is less than 9 and adds a leading 0
    public String toTime(int number) {
        if (number < 0) {
            number = 0;
        }
        return number <= 9 ? "0" + number : String.valueOf(number);
    }


    public void pauseTimer() {
        cdt.cancel();
        pause.setAlpha(1.0f);
    }

    public void resumeTimer() {
        timer(timeLeft);
        pause.setAlpha(0.0f);

    }

    public String getcurrentExervise() {
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

    //Handles pausing videos/timer on incoming calls and switching apps
    @Override
    protected void onPause() {
        super.onPause();
        if (!isVidBreak) {
            pausevid();
        }
        if (isVidBreak) {
            vidBreaktmr.cancel();

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isVidBreak) {
            vidBreak(vidBreakTimeLeft);
        }

    }


    public String nameClean(String exName) {


        if (exName.contains("_")) {
            exName = exName.replace("_", " ");
        }
        exName = exName.substring(1);
        exName = exName.substring(0, 1).toUpperCase() + exName.substring(1);
        return exName;
        //Log.d("ftest", exName);
    }

    //getter for hashmap
    public HashMap<String, Integer> getLikeMap() {
        return likeHash;
    }





       /* if(nameList.contains(fbaseString)){

            customWorkout.add(fbaseString);
        }

        return customWorkout;*/


    public void printarray() {



        exercises = output.split(",");


        for (String e : exercises) {


            for (int z = 0; z < nameList.size(); z++) {

                String noSpace = nameList.get(z).replace("_", " ");
                String noFirstLetter = noSpace.substring(1);
                Log.d("listReplace", noFirstLetter);

                if (e.contains(noFirstLetter)) {

                        customWorkout.add(nameList.get(z));

                    Log.d("qqqq", customWorkout.toString());


                }
            }
        }
        Log.d("chrinese",customWorkout.toString());

        nameList.clear();

        for (String s : customWorkout) {

            nameList.add(s);

        }

        Log.d("kindle", customWorkout.toString());

    }

    public void resetLikebt() {
        likeBtn.setAlpha(0.5f);
        dissLikeBtn.setAlpha(0.5f);
    }

    public void setEnabledBtn() {


        if (likeBtn.isEnabled()) {
            likeBtn.setEnabled(false);
            dissLikeBtn.setEnabled(false);
            Log.d("neando", "likedisabled");
        } else if (!likeBtn.isEnabled()) {
            Log.d("neando", "likeenlabled");
            likeBtn.setEnabled(true);
            dissLikeBtn.setEnabled(true);
        }
    }

    public void setWarmUpList(){
        warmUpList = new ArrayList<>();
        warmUpList.add("emarching");
        warmUpList.add("ewide_marching");
        warmUpList.add("emarching");
        warmUpList.add("aheel_digs_with_curl");
        warmUpList.add("emarching");
        warmUpList.add("ehand_to_knee");
        warmUpList.add("emarching");
    }
}



