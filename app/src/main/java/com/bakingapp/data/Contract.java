package com.bakingapp.data;

import android.net.Uri;
import android.provider.BaseColumns;
import android.support.test.espresso.core.deps.guava.collect.ImmutableList;

/**
 * Created by Mohamed Elgendy on 21/5/2017.
 */

public class Contract {

    static final String AUTHORITY = "com.udacity.baking";
    static final String PATH_RECIPE = "recipe";
    private static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    private Contract() {
    }

    @SuppressWarnings("unused")
    public static final class RecipeContract implements BaseColumns {

        public static final Uri URI = BASE_URI.buildUpon().appendPath(PATH_RECIPE).build();
        public static final String COLUMN_RECIPE = "recipe";
        public static final String COLUMN_RELATED_RECIPE = "relatedRecipe";
        public static final int POSITION_ID = 0;
        public static final int POSITION_RECIPE = 1;
        public static final int POSITION_RELATED_RECIPE = 2;
        public static final ImmutableList<String> RECIPE_COLUMNS = ImmutableList.of(
                _ID,
                COLUMN_RECIPE,
                COLUMN_RELATED_RECIPE
        );
        static final String TABLE_NAME = "recipes";

        public static Uri makeUriForRecipe(String recipe) {
            return URI.buildUpon().appendPath(recipe).build();
        }

        static String getRecipeFromUri(Uri queryUri) {
            return queryUri.getLastPathSegment();
        }
    }

}

