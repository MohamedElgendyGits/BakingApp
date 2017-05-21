package com.bakingapp.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bakingapp.R;
import com.bakingapp.model.Ingredients;

import java.util.ArrayList;

/**
 * Created by Mohamed Elgendy on 18/5/2017.
 */

public class RecipeIngredientsListAdapter extends RecyclerView.Adapter<RecipeIngredientsListAdapter.DataObjectHolder> {

    private ArrayList<Ingredients> mDataSet;
    private static RecipeIngredientsListAdapter.RecipeIngredientsClickListener recipeIngredientsClickListener;
    private Context context;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView ingredientQuantityTextView;
        TextView ingredientMeasureTextView;
        TextView ingredientDescriptionTextView;


        public DataObjectHolder(View itemView) {
            super(itemView);

            ingredientQuantityTextView = (TextView) itemView.findViewById(R.id.textView_quantity);
            ingredientMeasureTextView = (TextView) itemView.findViewById(R.id.textView_measure);
            ingredientDescriptionTextView = (TextView) itemView.findViewById(R.id.textView_ingredient);

            // adding Listener...
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recipeIngredientsClickListener.onRecipeIngredientClick(getAdapterPosition(), v);
        }
    }

    public RecipeIngredientsListAdapter(ArrayList<Ingredients> mDataSet, RecipeIngredientsListAdapter.RecipeIngredientsClickListener recipeIngredientsClickListener) {
        this.mDataSet = mDataSet;
        this.recipeIngredientsClickListener = recipeIngredientsClickListener;
    }

    @Override
    public RecipeIngredientsListAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_ingredient, parent, false);
        context = parent.getContext();

        RecipeIngredientsListAdapter.DataObjectHolder dataObjectHolder = new RecipeIngredientsListAdapter.DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(RecipeIngredientsListAdapter.DataObjectHolder holder, int position) {

        String quantity = mDataSet.get(position).getQuantity();
        String measure = mDataSet.get(position).getMeasure();
        String ingredient = mDataSet.get(position).getIngredient();

        holder.ingredientQuantityTextView.setText(quantity);
        holder.ingredientMeasureTextView.setText(measure);
        holder.ingredientDescriptionTextView.setText(ingredient);
    }

    public void addItem(Ingredients dataObj, int index) {
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

    public interface RecipeIngredientsClickListener {
        void onRecipeIngredientClick(int position, View v);
    }
}

