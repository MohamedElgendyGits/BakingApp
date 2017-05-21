package com.bakingapp.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bakingapp.R;
import com.bakingapp.model.Steps;

import java.util.ArrayList;

/**
 * Created by Mohamed Elgendy on 18/5/2017.
 */

public class RecipeStepsListAdapter extends RecyclerView.Adapter<RecipeStepsListAdapter.DataObjectHolder> {

    private ArrayList<Steps> mDataSet;
    private static RecipeStepsListAdapter.RecipeStepClickListener recipeStepClickListener;
    private Context context;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recipeStepTextView;

        public DataObjectHolder(View itemView) {
            super(itemView);

            recipeStepTextView = (TextView) itemView.findViewById(R.id.textView_recipe_step);

            // adding Listener...
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recipeStepClickListener.onRecipeStepClick(getAdapterPosition(), v);
        }
    }

    public RecipeStepsListAdapter(ArrayList<Steps> mDataSet, RecipeStepsListAdapter.RecipeStepClickListener recipeStepClickListener) {
        this.mDataSet = mDataSet;
        this.recipeStepClickListener = recipeStepClickListener;
    }

    @Override
    public RecipeStepsListAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_step, parent, false);
        context = parent.getContext();

        RecipeStepsListAdapter.DataObjectHolder dataObjectHolder = new RecipeStepsListAdapter.DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(RecipeStepsListAdapter.DataObjectHolder holder, int position) {

        String recipeStepShortDescriptionName = mDataSet.get(position).getShortDescription();

        holder.recipeStepTextView.setText(recipeStepShortDescriptionName);
    }

    public void addItem(Steps dataObj, int index) {
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

    public interface RecipeStepClickListener {
        void onRecipeStepClick(int position, View v);
    }
}
