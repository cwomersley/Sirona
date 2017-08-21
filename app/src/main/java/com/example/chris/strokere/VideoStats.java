package com.example.chris.strokere;

import android.support.annotation.NonNull;
import android.util.Log;


import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;


/**
 * Created by Chris on 12/07/2017.
 */



public class VideoStats {


    private static HashMap<String, Integer> videoLikes2 = new HashMap<String, Integer>();
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private String userID;
    private String output;
    private ExerciseView exerciseView;


    VideoStats() {
        exerciseView = new ExerciseView();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        getDbLike();



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



    public void getDbLike(){

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if (snapshot.getKey().equals("ExerciseLikes")) {
                        Object object = snapshot.child(userID).getValue();
                        if (object != null) {
                            output = snapshot.child(userID).getValue().toString();
                        }
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public void updateDbLikes(){


        getDbLike();
        splitString();
;

    }

    //splits the string from the database into name and value and stores in a map
    public void splitString(){

        if(output != null) {
            String[] split = output.split(",");

            for (int x = 0; x < split.length; x++) {

                String splitString = split[x];

                String[] nameValues = splitString.split("=");
                String scoreString = nameValues[1].replace("}", "");
                int score = Integer.parseInt(scoreString);
                String name = nameValues[0].replace("{","");
                String nameNoSpace = name.trim();
                videoLikes2.put(nameNoSpace, score);



            }

        }




        HashMap<String,Integer> currentLikes = exerciseView.getLikeMap();
        String oo = Integer.toString(currentLikes.size());

        for (String key: currentLikes.keySet()){




                if (videoLikes2.containsKey(key) && videoLikes2.get(key) != null) {
                    Log.d("edwatd", key);
                    if (currentLikes.get(key) == 1) {
                        int addLike = videoLikes2.get(key) + 1;
                        mDatabase.child("ExerciseLikes").child(userID).child(key).setValue(addLike);
                        Log.d("edsphone", "likeUpdated");
                    } else if (currentLikes.get(key) == -1) {
                        int minusLike = videoLikes2.get(key) - 1;
                        mDatabase.child("ExerciseLikes").child(userID).child(key).setValue(minusLike);
                    }

                    //update firbease entry


                } else {
                    //add to firebase as new entry


                    mDatabase.child("ExerciseLikes").child(userID).child(key).setValue(currentLikes.get(key));
                }


        }







    }






}

