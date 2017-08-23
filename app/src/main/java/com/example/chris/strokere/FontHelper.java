package com.example.chris.strokere;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Sam on 13/06/2017.
 */

public class FontHelper {

    /**
     * Sets up
     * @param context the application context
     * @return the Lato typeface
     */
    public static Typeface getLatoRegular(Context context){
        return Typeface.createFromAsset(context.getAssets(), "Lato-Regular.ttf");
    }


}