package com.example.shinecards.utils;

import android.app.Activity;

/**
 * Created by Lwdthe1 on 2/23/2015.
 */
public class StaticMethods {

    public static int getColorFromResources(Activity activity, int colorId) {
        return activity.getResources().getColor(colorId);
    }

}
