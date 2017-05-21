package com.bakingapp.view.fragments;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bakingapp.R;
import com.bakingapp.application.BakingConstants;
import com.bakingapp.controller.BakingController;
import com.bakingapp.controller.OnRecipeRetrieved;
import com.bakingapp.data.Contract;
import com.bakingapp.model.Recipe;
import com.bakingapp.utils.DialogUtils;
import com.bakingapp.utils.JsonUtils;
import com.bakingapp.view.activities.DetailActivity;
import com.bakingapp.view.activities.MainActivity;
import com.bakingapp.view.adapters.RecipeListAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by Mohamed Elgendy on 18/5/2017.
 */

public class RecipeFragment extends Fragment implements RecipeListAdapter.RecipeClickListener {

    // declare recipe list components
    private RecyclerView recipeRecyclerView;
    private RecyclerView.Adapter recipeAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Recipe> recipeArrayList;

    // declare progress dialog
    private ProgressDialog progressDialog;


    public RecipeFragment() {
        //required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);

        // initialize recipe recycler view
        recipeRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView_recipe);
        recipeRecyclerView.setHasFixedSize(true);

        if(rootView.findViewById(R.id.textView_title_phone) != null){
            mLayoutManager = new LinearLayoutManager(getActivity());
        }else{
            mLayoutManager = new GridLayoutManager(getActivity(),3);
        }

        recipeRecyclerView.setLayoutManager(mLayoutManager);
        recipeArrayList = new ArrayList<>();
        recipeAdapter = new RecipeListAdapter(recipeArrayList, this);
        recipeRecyclerView.setAdapter(recipeAdapter);


        retrieveRecipesFromServer();

        return rootView;
    }

    private void retrieveRecipesFromServer() {

        showProgressLoading();

        BakingController bakingController = new BakingController(new OnRecipeRetrieved() {
            @Override
            public void onSuccess(ArrayList<Recipe> recipe) {
                recipeArrayList.addAll(recipe);
                recipeAdapter.notifyDataSetChanged();


                hideProgressLoading();


                addDataToWidget(recipeArrayList);
            }

            @Override
            public void onFail(String errorMessage) {
                Log.d("Error",errorMessage);

                hideProgressLoading();
            }
        });
        bakingController.submitRetrieveRecipesRequest(((MainActivity)getActivity()).mIdlingResource);
    }


    public void showProgressLoading() {
        if (progressDialog == null)
            progressDialog = DialogUtils.getProgressDialog(getActivity(), getString(R.string.loading_message), false,
                    false);

        progressDialog.show();
    }

    public void hideProgressLoading() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    @Override
    public void onRecipeClick(int position, View v) {

        Recipe recipe = recipeArrayList.get(position);

        Intent detailActivity = new Intent(getActivity(), DetailActivity.class);
        String recipeJsonString = JsonUtils.convertObjectToJsonString(recipe,Recipe.class);
        detailActivity.putExtra(BakingConstants.MAIN_DETAIL_INTENT_KEY,recipeJsonString);
        startActivity(detailActivity);
    }

    private void addDataToWidget(ArrayList<Recipe> recipeArrayList){

        getActivity().getContentResolver().delete(Contract.RecipeContract.URI,null,null);

        ArrayList<ContentValues> ingredientsCVs = new ArrayList<>();

        for(int i=0;i<recipeArrayList.size();i++){
            ContentValues ingredientCV = new ContentValues();
            ingredientCV.put(Contract.RecipeContract.COLUMN_RECIPE, recipeArrayList.get(i).getName());
            String recipeJsonString = JsonUtils.convertObjectToJsonString(recipeArrayList.get(i),Recipe.class);
            ingredientCV.put(Contract.RecipeContract.COLUMN_RELATED_RECIPE, recipeJsonString);

            ingredientsCVs.add(ingredientCV);
        }

       getActivity().getContentResolver().bulkInsert(
                Contract.RecipeContract.URI,
                ingredientsCVs.toArray(new ContentValues[ingredientsCVs.size()]));
    }
}
