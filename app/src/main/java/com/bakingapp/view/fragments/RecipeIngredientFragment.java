package com.bakingapp.view.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bakingapp.R;
import com.bakingapp.application.BakingConstants;
import com.bakingapp.model.Ingredients;
import com.bakingapp.model.Steps;
import com.bakingapp.view.adapters.RecipeIngredientsListAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import static com.bakingapp.application.BakingConstants.BUNDLE_RECYCLER_LAYOUT;

/**
 * Created by Mohamed Elgendy on 18/5/2017.
 */

public class RecipeIngredientFragment extends Fragment implements RecipeIngredientsListAdapter.RecipeIngredientsClickListener {



    // declare steps list components
    private RecyclerView recipeIngredientsRecyclerView;
    private RecyclerView.Adapter recipeIngredientsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Ingredients> ingredientsArrayList;

    public RecipeIngredientFragment() {
        //required empty constructor
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String recipeIngredientJsonString = bundle.getString(BakingConstants.DETAIL_INGREDIENT_INTENT_KEY);
            ingredientsArrayList = new Gson().fromJson(recipeIngredientJsonString,
                    new TypeToken<ArrayList<Ingredients>>() {}.getType());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_details_ingredient, container, false);


        // initialize ingredients recycler view
        recipeIngredientsRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView_gradient);
        recipeIngredientsRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recipeIngredientsRecyclerView.setLayoutManager(mLayoutManager);
        recipeIngredientsAdapter = new RecipeIngredientsListAdapter(ingredientsArrayList, this);
        recipeIngredientsRecyclerView.setAdapter(recipeIngredientsAdapter);

        return rootView;
    }

    @Override
    public void onRecipeIngredientClick(int position, View v) {
        // nothing to do
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState != null)
        {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            recipeIngredientsRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, recipeIngredientsRecyclerView.getLayoutManager().onSaveInstanceState());
    }
}
