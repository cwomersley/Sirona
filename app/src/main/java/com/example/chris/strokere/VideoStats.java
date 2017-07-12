package com.example.chris.strokere;

import java.util.HashMap;

/**
 * Created by Chris on 12/07/2017.
 */




public class VideoStats {

    private HashMap likeOrDislike = new HashMap();




    public HashMap getLikeMap(){

        return likeOrDislike;
    }

    public void setLikes(int value, String exerciseName){

        if(likeOrDislike.containsKey(exerciseName)){
            likeOrDislike.put(exerciseName, value);
        }
    }


    public void addVideos(){
        String exerciseName;
        //foreachloop
        if (!likeOrDislike.containsKey(exerciseName)){

        }else{
            likeOrDislike.put(exerciseName,0);
        }

    }


}
