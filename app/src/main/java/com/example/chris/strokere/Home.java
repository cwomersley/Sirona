package com.example.chris.strokere;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Home
 *
 * <P>Main menu. Also displays tips that will take the user to the relevant page if they tap on
 * them.
 *
 * @author Sam Ward
 */
public class Home extends BaseActivity {

    /**
     * Home
     *
     * <P>The main navigation menu
     *
     * @author Sam Ward
     */

    private Tip tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupNavbar();

        Button btnWorkout = (Button) findViewById(R.id.btnWorkoutH);
        btnWorkout.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        btnWorkout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Home.this, WorkoutMenu.class));
            }
        });

        Button btnExercise = (Button) findViewById(R.id.ExerciseListBtn);
        btnExercise.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        btnExercise.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Home.this, ExerciseList.class));
            }
        });

        Button btnProgress = (Button) findViewById(R.id.ProgressBtn);
        btnProgress.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        btnProgress.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Home.this, ProgressMenu.class));
            }
        });

        Button btnPreferences = (Button) findViewById(R.id.Settings);
        btnPreferences.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        btnPreferences.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Settings.class));
            }
        });

        findATip();

    }

    /**
     * Chooses a tip at random and displays it
     */
    private void findATip() {

        TextView tipsText = (TextView) findViewById(R.id.tipsText);
        TipManager tipManager = new TipManager();
        //Uses the TipManager to get a random tip
        this.tip = tipManager.getATip();
        //Gets the text of the tip
        String tipText = this.tip.getTipText();
        //Gets the activity that the tip should go to
        String tipIntent = this.tip.getTipIntent();
        tipsText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goSomewhere();
            }
        });
        tipsText.setText(tipText);
        tipsText.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        //Log.d("Logthis   ",tip);

    }

    @Override
    public void onResume() {
        super.onResume();

        findATip();
    }

    /**
     * Goes to the activity associated with the displayed tip
     */
    private void goSomewhere() {

        Intent intent = new Intent();
        intent.setClassName(Home.this,this.tip.getTipIntent());
        startActivity(intent);
    }

    /**
     * Gets the active activity
     * @return this activity
     */
    @Override
    public int getLayout() {
        return R.layout.activity_home;
    }


}
