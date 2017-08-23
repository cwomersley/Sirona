package com.example.chris.strokere;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * ExerciseView
 *
 * <P>Handles video files and plays them in the video player. Methods for options relating to the videos(Likes/Rating/pause) </p>
 *
 *
 * @author Christopher Womersley
 */
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


        videoStats = new VideoStats();
        likeBtn = (ImageButton) findViewById(R.id.thumbsUpBtn);
        dissLikeBtn = (ImageButton) findViewById(R.id.thumbsDownbtn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        pause = (ImageButton) findViewById(R.id.pauseResume);
        timerText = (TextView) findViewById(R.id.timerText);
        exerciseNameText = (TextView) findViewById(R.id.exerciseNameText);
        exerciseNameText.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        pressedButton = getIntent().getExtras().getString("workChoice");
        workoutMenu = new WorkoutMenu();
        output = workoutMenu.getOutput();

        likeBtn.setAlpha(0.5f);
        dissLikeBtn.setAlpha(0.5f);


        mDatabase = FirebaseDatabase.getInstance().getReference();


        //Used to add files to db
        //populateDB();

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        //Check for which button is pressed on the previous menu screen
        if (pressedButton.equals("customWorkout")) {
            setUpExerciseList();
            customArray();
            setAndPlayVideo(setStringPath());
            timer(60000);
            progressBar.setProgress(i);

        } else if
                (pressedButton.equals("standard")) {
            setUpExerciseList();
            setAndPlayVideo(setStringPath());
            timer(60000);
            progressBar.setProgress(i);
        }

        //Listeners listen for click on Like/Dislike buttons
        //adds the likes and dislikes to a hashmap with the current exercise name as a key
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


    /**
     *set the current exercise playing in the video view
     * enables the video to be paused when the screen is clicked
     * @param vidPath the full path that videoView can use
     */
    private void setAndPlayVideo(String vidPath) {

        videoView = (VideoView) findViewById(R.id.videoViewE);
        videoView.setVideoPath(vidPath);
        videoView.start();
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


    /**
     *
     *gets the first element in the name list video array and converts to usable uri path
     * @return a usable path that the media player can use
     */
    private String setStringPath() {

        if (!nameList.isEmpty()) {
            String path = nameList.get(0);
            String stringPath = "android.resource://" + getPackageName() + "/" + "/raw/" + path;

            return stringPath;
        } else {
            return null;
        }


    }


    /**
     *
     * Adds the files to an array
     * shuffles the files and removes videos to make exercises vary
     * adds the warmup exercises to the start of the list
     */
    private void setUpExerciseList() {

        addFiles();
        Collections.shuffle(nameList);

         setWarmUpList();

        for(String s:warmUpList) {
        int x = 0;
        nameList.add(x, s);
        x++;
         }

        for(int z = 10; z<21 ;z++) {
        nameList.remove(z);
         }
    }

    /**
     * Creates a timer for the exercise video player
     * has methods to set the vid break timer and make it visible when the exercise is finished
     * @param timeLeftMilli the time left for when the video timer needs to be restarted after an interruption
     */
    private void timer(long timeLeftMilli) {

        setExText();

        cdt = new CountDownTimer(timeLeftMilli, 600) {
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                i++;
                progressBar.setProgress(i);
            }

            public void onFinish() {

                if (nameList.size() > 1) {
                    resetLikebt();
                    videoView.setVisibility(View.INVISIBLE);
                    vidBreak(10000l);
                    cdt.cancel();
                    isVidBreak = true;
                    pause.setClickable(false);
                    exerciseNameText.setText("Next: " + nameClean(nameList.get(1)));
                    setEnabledBtn();

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


    /**
     * Creates a timer object that handles the break in between videos
     * Saves the current time incase of interruption and decreases the time to displayed in text view
     * When the timer is finished handles the playing of the next video in the workout
     * @param breaktimeLeft takes the time left when restarting the timer after interuption of the activity
     */
    private void vidBreak(Long breaktimeLeft) {
        timerText.setVisibility(View.VISIBLE);
        vidBreaktmr = new CountDownTimer(breaktimeLeft, 990) {

            public void onTick(long millisUntilFinished) {
                vidBreakTimeLeft = millisUntilFinished;
                timerText.setText(toTime(time));
                time--;

            }

            /**
             * Contains methods that set the next video to be played
             * Restarts the exercise video timer
             * Sets the pause button to be clickable
             */
            public void onFinish() {

                isVidBreak = false;
                nameList.remove(0);
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


    /**
     *  Adds a leading zero to the number when it is below 10 seconds
     * @param number time remaining
     * @return number displayed in seconds with leading zero if required
     */
    private String toTime(int number) {
        if (number < 0) {
            number = 0;
        }
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    /**
     * Pauses the video
     * Cancels the video timer
     * Displays the pause Button
     */
    private void pauseTimer() {
        cdt.cancel();
        pause.setAlpha(1.0f);
    }


    /**
     * Resumes the current video playing with the saved time
     * Hides the pause button
     */
    private void resumeTimer() {
        timer(timeLeft);
        pause.setAlpha(0.0f);

    }



    /**
     * Checks if the video is playing or paused
     * Pauses plays video depending on current state
     */
    private void pausevid() {
        if (videoView.isPlaying()) {
            videoView.pause();
            pauseTimer();


        } else {
            videoView.start();
            resumeTimer();
        }
    }



    /**
     * Handles when the activity is interrupted(Phone call ect)
     * Pauses the video playing or cancels the video break timer
     */
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

    /**
     * Handles what happens when the current activity is resumed
     * Checks time left for the exercise break and resumes the activity with the previously paused time
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (isVidBreak) {
            vidBreak(vidBreakTimeLeft);
        }

    }

    /**
     * Method to clean name from video string to display above the video
     * @param exName exercise name string to be modified
     * @return a readable name to be used in the text view
     */
    private String nameClean(String exName) {

        if (exName.contains("_")) {
            exName = exName.replace("_", " ");
        }
        exName = exName.substring(1);
        exName = exName.substring(0, 1).toUpperCase() + exName.substring(1);
        return exName;
    }

    /**
     * Sets the name in the exercise text above the video after cleaning it
     */
    private void setExText() {

        exerciseNameText.setText(nameClean(nameList.get(0)));
    }


    /**
     * Getter for the hashmap for like/dislikes of exercise videos
     * @return hashmap of liked/disliked videos
     */
    public HashMap<String, Integer> getLikeMap() {
        return likeHash;
    }

    /**
     * Method to handle the output data from Firebase and add selected exercise videos to the nameList array
     * output is the String data received from Firebase it is pre loaded in exercise menu activity
     */
    private void customArray() {

        exercises = output.split(",");

        for (String e : exercises) {
            for (int z = 0; z < nameList.size(); z++) {

                String noSpace = nameList.get(z).replace("_", " ");
                String noFirstLetter = noSpace.substring(1);

                if (e.contains(noFirstLetter)) {
                   customWorkout.add(nameList.get(z));
                }
            }
        }
        nameList.clear();
        for (String s : customWorkout) {
            nameList.add(s);
        }
    }


    /**
     * Resets the like/dislike buttons alpha to 50%
     */
    private void resetLikebt() {
        likeBtn.setAlpha(0.5f);
        dissLikeBtn.setAlpha(0.5f);
    }


    /**
     *Checks if the like/dislike buttons are enabled and sets them to their opposite states
     */
    private void setEnabledBtn() {

        if (likeBtn.isEnabled()) {
            likeBtn.setEnabled(false);
            dissLikeBtn.setEnabled(false);

        } else if (!likeBtn.isEnabled()) {

            likeBtn.setEnabled(true);
            dissLikeBtn.setEnabled(true);
        }
    }

    /**
     * Adds the file names from raw to an array list
     */
    public void addFiles(){
        Field[] fields = R.raw.class.getFields();

        for (int i = 0; i < fields.length - 1; i++) {
            String name = fields[i].getName();
            if (name != "$change" && name != "serialVersionUID") {
                nameList.add(name);
            }
        }
    }


    /**
     *Adds specified exercises to a arrayList used for the warm up
     */
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

    /**
     * Needed for the nav bar
     * @return
     */
    @Override
    public int getLayout() {
        return R.layout.activity_home;
    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Method that was used to populate the database with the files added to raw folder
     * Currently only used when new files will be added
     */
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

}



