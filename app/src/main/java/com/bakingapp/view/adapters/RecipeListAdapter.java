package com.bakingapp.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bakingapp.R;
import com.bakingapp.model.Recipe;

import java.util.ArrayList;

/**
 * Created by Mohamed Elgendy on 18/5/2017.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.DataObjectHolder> {

    private ArrayList<Recipe> mDataSet;
    private static RecipeClickListener recipeClickListener;
    private Context context;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recipeTextView;

        public DataObjectHolder(View itemView) {
            super(itemView);

            recipeTextView = (TextView) itemView.findViewById(R.id.textView_recipe);

            // adding Listener...
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recipeClickListener.onRecipeClick(getAdapterPosition(), v);
        }
    }

    public RecipeListAdapter(ArrayList<Recipe> mDataSet, RecipeClickListener recipeClickListener) {
        this.mDataSet = mDataSet;
        this.recipeClickListener = recipeClickListener;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recipe, parent, false);
        context = parent.getContext();

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        String recipeName = mDataSet.get(position).getName();

        holder.recipeTextView.setText(recipeName);
    }

    public void addItem(Recipe dataObj, int index) {
        mDataSet.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataSet.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public interface RecipeClickListener {
        void onRecipeClick(int position, View v);
    }
}


