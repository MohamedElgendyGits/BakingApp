package com.bakingapp.view.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bakingapp.R;
import com.bakingapp.application.BakingConstants;
import com.bakingapp.model.Recipe;
import com.bakingapp.model.Steps;
import com.bakingapp.utils.JsonUtils;
import com.bakingapp.view.adapters.RecipeStepsListAdapter;
import com.bakingapp.view.interfaces.OnIngredientSelectedListener;
import com.bakingapp.view.interfaces.OnStepSelectedListener;

import java.util.ArrayList;

/**
 * Created by Mohamed Elgendy on 18/5/2017.
 */

public class RecipeDetailsFragment extends Fragment implements RecipeStepsListAdapter.RecipeStepClickListener,
        View.OnClickListener {

    private OnIngredientSelectedListener onIngredientSelectedListener;
    private OnStepSelectedListener onStepSelectedListener;

    Recipe mRecipe;
    private TextView recipeIngredientsTextView;

    // declare steps list components
    private RecyclerView recipeStepsRecyclerView;
    private RecyclerView.Adapter recipeStepsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Steps> recipeStepsArrayList;

    public RecipeDetailsFragment() {
        //required empty constructor
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String recipeJsonString = bundle.getString(BakingConstants.MAIN_DETAIL_INTENT_KEY);
            mRecipe = JsonUtils.convertJsonStringToObject(recipeJsonString, Recipe.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        recipeIngredientsTextView = (TextView) rootView.findViewById(R.id.textView_recipe_ingredients);
        recipeIngredientsTextView.setOnClickListener(this);

        // initialize recipe steps recycler view
        recipeStepsRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView_recipe_steps);
        recipeStepsRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recipeStepsRecyclerView.setLayoutManager(mLayoutManager);
        recipeStepsArrayList = mRecipe.getSteps();
        recipeStepsAdapter = new RecipeStepsListAdapter(recipeStepsArrayList, this);
        recipeStepsRecyclerView.setAdapter(recipeStepsAdapter);

        return rootView;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof OnIngredientSelectedListener) {
            onIngredientSelectedListener = (OnIngredientSelectedListener) activity;
        }
        if (activity instanceof OnStepSelectedListener) {
            onStepSelectedListener = (OnStepSelectedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement OnIngredientSelectedListener or OnStepSelectedListener");
        }
    }


    @Override
    public void onRecipeStepClick(int position, View v) {
        onStepSelectedListener.onStepSelected(recipeStepsArrayList.get(position));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView_recipe_ingredients:
                onIngredientSelectedListener.onIngredientSelected(mRecipe.getIngredients());
                break;
            default:
                break;
        }
    }
}
