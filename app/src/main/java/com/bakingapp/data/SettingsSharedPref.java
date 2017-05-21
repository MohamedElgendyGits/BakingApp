package com.bakingapp.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Mohamed Elgendy on 21/5/2017.
 */

public class SettingsSharedPref {

    public static final String PREFERENCE_DESIRED_RECIPE_KEY = "desiredRecipe";
    private static final boolean PREFERENCE_DESIRED_RECIPE_DEFAULT = false;


    private SettingsSharedPref() {
        // prevent any instance from this class
    }

    public static boolean getDesiredRecipe(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(PREFERENCE_DESIRED_RECIPE_KEY, PREFERENCE_DESIRED_RECIPE_DEFAULT);
    }

    public static void toggleDesiredRecipe(Context context, boolean isDesired) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(PREFERENCE_DESIRED_RECIPE_KEY, isDesired);
        editor.apply();
    }
}
