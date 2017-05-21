package com.bakingapp.controller;

import android.support.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bakingapp.IdlingResource.VolleyIdlingResource;
import com.bakingapp.application.BakingApplication;
import com.bakingapp.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Mohamed Elgendy on 17/5/2017.
 */

public class BakingController {

    private static final String RECIPE_LISTING_URL  = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private OnRecipeRetrieved onRecipeRetrieved;

    public BakingController(OnRecipeRetrieved onRecipeRetrieved) {
        this.onRecipeRetrieved = onRecipeRetrieved;
    }

    public void submitRetrieveRecipesRequest(@Nullable final VolleyIdlingResource mIdlingResource){


        /**
         * The IdlingResource is null in production as set by the @Nullable annotation which means
         * the value is allowed to be null.
         *
         * If the idle state is true, Espresso can perform the next action.
         * If the idle state is false, Espresso will wait until it is true before
         * performing the next action.
         */
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(false);
        }


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(RECIPE_LISTING_URL, new Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                // Get Gson object
                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                // parse json string to array of objects
                ArrayList<Recipe> recipeArrayList = gson.fromJson(response.toString(),
                        new TypeToken<ArrayList<Recipe>>() {}.getType());

                onRecipeRetrieved.onSuccess(recipeArrayList);


                if (mIdlingResource != null) {
                    mIdlingResource.setIdleState(true);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onRecipeRetrieved.onFail(error.getMessage());

                if (mIdlingResource != null) {
                    mIdlingResource.setIdleState(true);
                }
            }
        });

        // Adding request to request queue
        BakingApplication.getInstance().addToRequestQueue(jsonArrayRequest);
    }
}
