package com.bakingapp.controller;

import com.bakingapp.model.Recipe;

import java.util.ArrayList;

/**
 * Created by Mohamed Elgendy on 17/5/2017.
 */

public interface OnRecipeRetrieved {
    void onSuccess(ArrayList<Recipe> recipe);
    void onFail(String errorMessage);
}
