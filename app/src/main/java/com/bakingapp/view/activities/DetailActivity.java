package com.bakingapp.view.activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.bakingapp.R;
import com.bakingapp.application.BakingApplication;
import com.bakingapp.application.BakingConstants;
import com.bakingapp.data.Contract;
import com.bakingapp.model.Ingredients;
import com.bakingapp.model.Recipe;
import com.bakingapp.model.Steps;
import com.bakingapp.utils.FragmentUtils;
import com.bakingapp.utils.JsonUtils;
import com.bakingapp.view.fragments.RecipeDetailsFragment;
import com.bakingapp.view.fragments.RecipeFragment;
import com.bakingapp.view.fragments.RecipeIngredientFragment;
import com.bakingapp.view.fragments.RecipeStepFragment;
import com.bakingapp.view.interfaces.OnIngredientSelectedListener;
import com.bakingapp.view.interfaces.OnStepSelectedListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements OnIngredientSelectedListener,
        OnStepSelectedListener{

    String recipeJsonString;

    // Flag determines if this is a one or two pane layout
    private boolean isTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(savedInstanceState == null){

            if(getIntent().getExtras() != null) {
                String recipeJsonString = getIntent().getExtras().getString(BakingConstants.MAIN_DETAIL_INTENT_KEY);
                setupRecipeDetailsFragment(recipeJsonString);

                updateActionBarTitle(recipeJsonString);
            }

        }

        // Call this to determine which layout we are in (tablet or phone)
        if (findViewById(R.id.fragment_detail_content_container) != null) {
            isTwoPane = true;
        }

    }

    private void updateActionBarTitle(String recipeJsonString) {
        Recipe mRecipe = JsonUtils.convertJsonStringToObject(recipeJsonString, Recipe.class);
        getSupportActionBar().setTitle(mRecipe.getName());
    }

    private void setupRecipeDetailsFragment(String recipeJsonString) {

        // attach data to bundle
        Bundle bundle = new Bundle();
        bundle.putString(BakingConstants.MAIN_DETAIL_INTENT_KEY, recipeJsonString);
        // set bundle to the recipeDetails fragment
        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        recipeDetailsFragment.setArguments(bundle);

        FragmentUtils.replaceFragment(this,recipeDetailsFragment,R.id.fragment_detail_container,false,"");
    }

    @Override
    public void onIngredientSelected(ArrayList<Ingredients> ingredients) {

        // attach data to bundle
        Bundle bundle = new Bundle();
        String ingredientsJsonString = new Gson().toJson(ingredients, new TypeToken<ArrayList<Ingredients>>() {}.getType());
        bundle.putString(BakingConstants.DETAIL_INGREDIENT_INTENT_KEY, ingredientsJsonString);
        // set bundle to the recipeIngredients fragment
        RecipeIngredientFragment recipeIngredientFragment = new RecipeIngredientFragment();
        recipeIngredientFragment.setArguments(bundle);

        if (isTwoPane) {
            FragmentUtils.replaceFragment(this,recipeIngredientFragment,R.id.fragment_detail_content_container,false,"");
        }
        else {
            FragmentUtils.replaceFragment(this,recipeIngredientFragment,R.id.fragment_detail_container,true,"");
        }
    }

    @Override
    public void onStepSelected(Steps step) {

        // attach data to bundle
        Bundle bundle = new Bundle();
        String stepJsonString = JsonUtils.convertObjectToJsonString(step,Steps.class);
        bundle.putString(BakingConstants.DETAIL_STEP_INTENT_KEY, stepJsonString);
        // set bundle to the recipeSteps fragment
        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
        recipeStepFragment.setArguments(bundle);


        if (isTwoPane) {
            FragmentUtils.replaceFragment(this,recipeStepFragment,R.id.fragment_detail_content_container,false,"");
        }
        else {
            FragmentUtils.replaceFragment(this,recipeStepFragment,R.id.fragment_detail_container,true,"");
        }
    }
}
