package com.bakingapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mohamed Elgendy on 21/5/2017.
 */

public class DbHelper extends SQLiteOpenHelper{

    private static final String NAME = "BakingApp.db";
    private static final int VERSION = 1;


    DbHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String builder = "CREATE TABLE " + Contract.IngredientContract.TABLE_NAME + " ("
                + Contract.IngredientContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Contract.IngredientContract.COLUMN_QUANTITY + " TEXT NOT NULL,"
                + Contract.IngredientContract.COLUMN_MEASURE + " TEXT NOT NULL,"
                + Contract.IngredientContract.COLUMN_INGREDIENT + " TEXT NOT NULL" +
                ");";

        db.execSQL(builder);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + Contract.IngredientContract.TABLE_NAME);

        onCreate(db);
    }
}
