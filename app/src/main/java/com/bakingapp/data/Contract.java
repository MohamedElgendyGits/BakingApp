package com.bakingapp.data;

import android.net.Uri;
import android.provider.BaseColumns;
import android.support.test.espresso.core.deps.guava.collect.ImmutableList;

/**
 * Created by Mohamed Elgendy on 21/5/2017.
 */

public class Contract {

    static final String AUTHORITY = "com.udacity.baking";
    static final String PATH_INGREDIENT = "recipe";
    private static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    private Contract() {
    }

    @SuppressWarnings("unused")
    public static final class IngredientContract implements BaseColumns {

        public static final Uri URI = BASE_URI.buildUpon().appendPath(PATH_INGREDIENT).build();
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_MEASURE = "measure";
        public static final String COLUMN_INGREDIENT = "ingredient";

        public static final int POSITION_ID = 0;
        public static final int POSITION_QUANTITY = 1;
        public static final int POSITION_MEASURE = 2;
        public static final int POSITION_INGREDIENT = 3;
        public static final ImmutableList<String> INGREDIENT_COLUMNS = ImmutableList.of(
                _ID,
                COLUMN_QUANTITY,
                COLUMN_MEASURE,
                COLUMN_INGREDIENT
        );
        static final String TABLE_NAME = "ingredients";

        public static Uri makeUriForIngredient(String ingredient) {
            return URI.buildUpon().appendPath(ingredient).build();
        }

        static String getIngredientFromUri(Uri queryUri) {
            return queryUri.getLastPathSegment();
        }
    }

}

