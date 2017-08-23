package com.example.chris.strokere;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Home extends BaseActivity {

    /**
     * Home
     *
     * <P>The main navigation menu
     *
     * @author Sam Ward
     */

    Tip tip;

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

        Button btnExercise = (Button) findViewById(R.id.ShuttleRunBtn);
        btnExercise.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        btnExercise.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Home.this, ExerciseList.class));
            }
        });

        Button btnProgress = (Button) findViewById(R.id.StepUpsBtn);
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
