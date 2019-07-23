package com.postingapp.android.db;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    static final String TAG = "DbHelper";
    static final String DB_NAME = "db_voicere";
    static final int DB_VERSION = 1;
    static Activity activity;

    public DbHelper(Activity act) {
        super(act.getApplicationContext(), DB_NAME, null, DB_VERSION);
        activity = act;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS users("
                + "id INTEGER,"
                + "username TEXT,"
                + "photoUrl TEXT, "
                + "email TEXT,"
                + "likesCount double "
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
    }
}