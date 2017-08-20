package com.example.chris.strokere;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
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


import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Chris on 12/07/2017.
 */



public class VideoStats {


    private HashMap<String, Integer> videoLikes2;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private String userID;
    String output;
    String output2;
    private ArrayList stringList;
    private ExerciseView exerciseView;


    VideoStats() {
        exerciseView = new ExerciseView();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        videoLikes2 = new HashMap<String, Integer>();
        upDateLike();



        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    userID = user.getUid();
                }
            }
        });

    }



    public void upDateLike(){

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if (snapshot.getKey().equals("ExerciseLikes")) {
                        Object object = snapshot.child(userID).getValue();

                         output = snapshot.child(userID).getValue().toString();

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public String getSpanshot(){


        upDateLike();
        splitString();
        updateDb();
        String poo ="goober";
        return poo;

    }

    //splits the string from the database into name and value and stores in a map
    public void splitString(){
        upDateLike();
        if(output != null) {
            String[] split = output.split(",");

            for (int x = 0; x < split.length; x++) {

                String splitString = split[x];

                String[] nameValues = splitString.split("=");
                String scoreString = nameValues[1].replace("}", "");
                int score = Integer.parseInt(scoreString);
                String name = nameValues[0].replace("{","");

                videoLikes2.put(name, score);


            }

        }
    }

    public void updateDb(){

        HashMap<String,Integer> currentLikes = exerciseView.getLikeMap();
        String oo = Integer.toString(currentLikes.size());
        Log.d("eee", oo);
        for (String key: currentLikes.keySet()){


            if (videoLikes2.containsKey(key)){
                //update firbease entry

               // mDatabase.child("ExerciseLikes").child(userID).child(getTime()).setValue(borgRating);
            }else{
                //add to firebase as new entry


                mDatabase.child("ExerciseLikes").child(userID).child(key).setValue(currentLikes.get(key));
            }

        }

    }





}