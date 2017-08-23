package com.example.chris.strokere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * Class that opens and passes information to particular workout tests for the patient
 * @author Edward Boyles
 */

public class WorkoutTestMenu extends BaseActivity {

    private Button sitToStandsBtn;
    private Button shuttleRunBtn;
    private Button stepUpsBtn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_test_menu);
        setupNavbar();

        sitToStandsBtn = (Button) findViewById(R.id.sitToStandsBtn);
        shuttleRunBtn = (Button) findViewById(R.id.ShuttleRunBtn);
        stepUpsBtn = (Button) findViewById(R.id.StepUpsBtn);
        sitToStandsBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        shuttleRunBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        stepUpsBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));


    }

    /**
     * Gets the active activity
     * @return this activity
     */
    @Override
    public int getLayout() {
        return R.layout.activity_workout_test_menu;
    }

    /**
     * Opens sit to stands to test
     * @param view
     */
    public void sitToStands(View view) {

        Intent intent = new Intent(getApplicationContext(), WorkoutTest.class);
        intent.putExtra("button", "sitToStands");
        startActivity(intent);
    }

    /**
     * Opens shuttle run test
     * @param view
     */
    public void shuttleRun(View view) {
        Intent intent = new Intent(getApplicationContext(), WorkoutTest.class);
        intent.putExtra("button", "shuttleRun");
        startActivity(intent);

    }


    /**
     * Opens step ups test
     * @param view
     */
    public void stepUps(View view) {
        Intent intent = new Intent(getApplicationContext(), WorkoutTest.class);
        intent.putExtra("button", "stepUps");
        startActivity(intent);
    }
}
