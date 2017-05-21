package com.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bakingapp.R;
import com.bakingapp.application.BakingConstants;
import com.bakingapp.data.Contract;

/**
 * Created by Mohamed Elgendy on 21/5/2017.
 */

public class BakingWidgetData implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private Cursor cursor;
    private Intent intent;

    //For obtaining the activity's context and intent
    public BakingWidgetData(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    private void initCursor(){
        if (cursor != null) {
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        /**This is done because the widget runs as a separate thread
         when compared to the current app and hence the app's data won't be accessible to it */

        cursor = context.getContentResolver().query(Contract.RecipeContract.URI,
                Contract.RecipeContract.RECIPE_COLUMNS.toArray(new String[]{}),
                null, null, null);

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onCreate() {
        initCursor();
        if (cursor != null) {
            cursor.moveToFirst();
        }
    }

    @Override
    public void onDataSetChanged() {
        /** Listen for data changes and initialize the cursor again **/
        initCursor();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        /** Populate your widget's single list item **/
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.list_item_baking_widget);
        cursor.moveToPosition(i);

        remoteViews.setTextViewText(R.id.textView_recipe,cursor.getString(Contract.RecipeContract.POSITION_RECIPE));

        // set Onclick Item Intent
        Intent onClickItemIntent = new Intent();
        int recipeColumn = cursor.getColumnIndex(Contract.RecipeContract.COLUMN_RECIPE);
        int relatedRecipeColumn = cursor.getColumnIndex(Contract.RecipeContract.COLUMN_RELATED_RECIPE);
        //onClickItemIntent.putExtra(BakingConstants.MAIN_DETAIL_INTENT_KEY,cursor.getString(recipeColumn));
        onClickItemIntent.putExtra(BakingConstants.MAIN_DETAIL_INTENT_KEY,cursor.getString(relatedRecipeColumn));


        remoteViews.setOnClickFillInIntent(R.id.list_item_recipe_row,onClickItemIntent);


        return remoteViews;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public long getItemId(int i) {
        cursor.moveToPosition(i);
        return cursor.getLong(cursor.getColumnIndex(Contract.RecipeContract._ID));
    }

    @Override
    public void onDestroy() {
        if (cursor!=null)
            cursor.close();
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


}
