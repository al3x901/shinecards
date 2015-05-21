package com.example.shinecards.utils.files;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;import java.lang.String;

/**
 * Created by Lwdthe1 on 2/13/2015.
 */
public class SToast {
    public static void showL( Context c, String text){
        Toast.makeText(c, text, Toast.LENGTH_LONG).show();
    }
    public static void showS(Activity a, Context c, String text){
        Toast.makeText(c, text, Toast.LENGTH_SHORT).show();
    }
}
