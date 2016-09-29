package com.example.rajpratim.completehealthcare.Calories_detect.ECG;

/**
 * Created by RajPratim on 5/31/2016.
 */
import android.app.Activity;
import android.content.pm.ActivityInfo;

public class ActivityHelper {
    public static void initialize(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //.SCREEN_ORIENTATION_LANDSCAPE);
        //	activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}