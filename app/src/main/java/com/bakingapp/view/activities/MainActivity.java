package com.bakingapp.view.activities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bakingapp.IdlingResource.VolleyIdlingResource;
import com.bakingapp.R;
import com.bakingapp.controller.BakingController;
import com.bakingapp.controller.OnRecipeRetrieved;
import com.bakingapp.model.Recipe;
import com.bakingapp.utils.FragmentUtils;
import com.bakingapp.view.fragments.RecipeFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    // The Idling Resource which will be null in production.
    @Nullable
    public VolleyIdlingResource mIdlingResource;

    /**
     * Only called from test, creates and returns a new {@link VolleyIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new VolleyIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getIdlingResource();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FragmentUtils.replaceFragment(this,new RecipeFragment(),R.id.fragment_container,false,"");
    }
}
