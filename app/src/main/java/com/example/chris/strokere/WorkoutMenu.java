package com.example.chris.strokere;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * @author Christopher Womersley
 *
 * Menu for workouts and tests
 * Prelaods custom workout data if avaible
 * Enables the custom workout button
 */
public class WorkoutMenu extends BaseActivity {

    private Button StandardBtn;
    private Button TestBtn;
    private Button customBtn;
    private FirebaseUser user;
    private String userID;
    private static String output;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_menu);
        setupNavbar();


        StandardBtn = (Button) findViewById(R.id.StandardBtn);
        StandardBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        customBtn = (Button) findViewById(R.id.ShuttleRunBtn);
        customBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        customBtn.setAlpha(0.5f);
        customBtn.setEnabled(false);
        TestBtn = (Button) findViewById(R.id.StepUpsBtn);
        TestBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));


        mDatabase = FirebaseDatabase.getInstance().getReference();
        makeCustomWorkout();

    }

    /**
     * Starts standard activity on button press
     * @param view
     */
    public void standardWorkout(View view) {
        Intent intent = new Intent(WorkoutMenu.this, ExerciseView.class);
        intent.putExtra("workChoice", "standard");
        startActivity(intent);
    }

    /**
     * Starts test menu activity on button press
     * @param view
     */
    public void oTestMenu(View view) {
        startActivity(new Intent(WorkoutMenu.this, WorkoutTestMenu.class));
    }

    /**
     * Starts custom workout activity on button press
     * @param view
     */
    public void customWorkout(View view) {
        Intent intent = new Intent(WorkoutMenu.this, ExerciseView.class);

        intent.putExtra("workChoice", "customWorkout");
        startActivity(intent);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_workout_menu;
    }

    /**
     * Retrieves the custom workout data for the signed in user
     * Pre load and convert ot string to be used when user selects custom workout
     * Sets the button to clickable when the data is loaded
     */
    public void makeCustomWorkout() {

        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    userID = user.getUid();
                }
            }
        });
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if (snapshot.getKey().equals("CustomWorkouts")) {

                        Object object = snapshot.child(userID).getValue();
                        if (object != null) {

                            output = object.toString();
                            customBtn.setAlpha(1);
                            customBtn.setEnabled(true);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    public String getOutput(){
        return output;
    }
}