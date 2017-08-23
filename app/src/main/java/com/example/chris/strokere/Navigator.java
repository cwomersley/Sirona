package com.example.chris.strokere;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.Locale;

/**
 * Navigator
 *
 * <P>Used by the navbar to navigate between menus
 *
 * @author Sam Ward
 */

public class Navigator extends AppCompatActivity {

    private ListView drawerList;
    private DrawerLayout drawerLayout;
    private String[] menuArray;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator);

        ImageButton myExerciseNavBtn = (ImageButton) findViewById(R.id.myExerciseNavBtn);
        myExerciseNavBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Navigator.this, ExerciseView.class));
            }
        });

        ImageButton exerciseListNavBtn = (ImageButton) findViewById(R.id.exerciseListNavBtn);
        myExerciseNavBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Navigator.this, ExerciseList.class));
            }
        });

        //
        String[] navArray;
        navArray = new String[6];
        navArray[0] = "Main Menu";
        navArray[1] = "Exercise List";
        navArray[2] = "Progress";
        navArray[3] = "Account";


    }

}
