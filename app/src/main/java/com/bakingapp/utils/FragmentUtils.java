package com.bakingapp.utils;


import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by Mohamed Elgendy on 17/5/2017.
 */

public class FragmentUtils {

    /**
     * @param activity
     * @param fragment
     * @param container
     * @param isNeedToAddToStack true if you need to push this fragment to stack
     * @param fragmentTag
     */
    public static void replaceFragment(AppCompatActivity activity, Fragment fragment, @IdRes int container,
                                       boolean isNeedToAddToStack,
                                       String fragmentTag) {

        String fragTag = getFragmentTag(fragment, fragmentTag);
        FragmentManager manager = activity.getSupportFragmentManager();
        boolean isInStack = manager.popBackStackImmediate(fragTag, 0);
        FragmentTransaction ft = manager.beginTransaction();

        if (isInStack) {
            fragment = manager.findFragmentByTag(fragTag);
        }
        ft.replace(container, fragment, fragTag);
        if (!isInStack && isNeedToAddToStack) {
            ft.addToBackStack(fragTag);
        }

        ft.commit();
    }

    private static String getFragmentTag(Fragment fragment, String fragmentTag) {
        String backStateName;
        if (fragmentTag == null) {
            backStateName = fragment.getClass().getName();
        } else {
            backStateName = fragmentTag;
        }
        return backStateName;
    }
}
