package com.bakingapp.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RemoteViews;

import com.bakingapp.R;
import com.bakingapp.application.BakingApplication;
import com.bakingapp.application.BakingConstants;
import com.bakingapp.data.SettingsSharedPref;
import com.bakingapp.view.activities.DetailActivity;
import com.bakingapp.view.activities.MainActivity;

/**
 * Created by Mohamed Elgendy on 21/5/2017.
 */

public class BakingWidgetProvider extends AppWidgetProvider {

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId) {
        // Construct the RemoteViews object which defines the view of out widget
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);
        // Instruct the widget manager to update the widget
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            setRemoteAdapter(context, views);
        } else {
            setRemoteAdapterV11(context, views);
        }

        /** PendingIntent to launch the MainActivity when the widget was clicked **/
        Intent launchMain = new Intent(context, MainActivity.class);
        PendingIntent pendingMainIntent = PendingIntent.getActivity(context, 0, launchMain, 0);
        views.setOnClickPendingIntent(R.id.widget_toolbar, pendingMainIntent);


        if(! SettingsSharedPref.getDesiredRecipe(BakingApplication.getInstance())) {
            views.setViewVisibility(R.id.textView_widget_hint, View.VISIBLE);
        }else{
            views.setViewVisibility(R.id.textView_widget_hint, View.GONE);
        }

        /*
        // Open ingredients on List Item click
        Intent intentRecipeDetails = new Intent(context, DetailActivity.class);
        PendingIntent pendingIntent = TaskStackBuilder.create(context)
                .addNextIntentWithParentStack(intentRecipeDetails)
                .getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.baking_widget_listView,pendingIntent);
        */


        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.baking_widget_listView);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    /** Set the Adapter for out widget **/

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private static void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.baking_widget_listView,
                new Intent(context, BakingWidgetService.class));
    }


    /** Deprecated method, don't create this if you are not planning to support devices below 4.0 **/
    @SuppressWarnings("deprecation")
    private static void setRemoteAdapterV11(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(0, R.id.baking_widget_listView,
                new Intent(context, BakingWidgetService.class));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        // Receive Broadcast About Stock Data Update
        if (intent.getAction().equals(BakingConstants.ACTION_DATA_UPDATED)){
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context,getClass()));
            // update All Widgets
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.baking_widget_listView);
        }
    }
}