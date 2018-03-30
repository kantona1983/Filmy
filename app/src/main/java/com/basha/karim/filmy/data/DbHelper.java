package com.basha.karim.filmy.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.basha.karim.filmy.data.Contract.MovieEntry.COLUMN_MOVIEID;
import static com.basha.karim.filmy.data.Contract.MovieEntry.COLUMN_NAME;
import static com.basha.karim.filmy.data.Contract.MovieEntry.TABLE_NAME;

/**
 * Created by karim on 3/15/2018.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "movieList.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT,"
                + COLUMN_MOVIEID + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
