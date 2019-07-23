package com.postingapp.android.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.postingapp.android.model.Profile;

import java.util.ArrayList;

public class Queries {

    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public Queries(SQLiteDatabase db, DbHelper dbHelper) {
        this.db = db;
        this.dbHelper = dbHelper;
    }

    public void deleteTable(String tableName) {
        db = dbHelper.getWritableDatabase();
        try{
            db.delete(tableName, null, null);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        db.close();
    }

    public void insertUser(Profile entry) {
        db = dbHelper.getWritableDatabase();

        if(getUserByUser(entry) == null) {
            ContentValues values = new ContentValues();
            values.put("id", entry.getId());
            values.put("email", entry.getEmail());
            values.put("username", entry.getUsername());
            values.put("photoUrl", entry.getPhotoUrl());
            values.put("likesCount", entry.getLikesCount());
            db.insert("users", null, values);
        }
        else
            updateUser(entry);
    }

    public void updateUser(Profile entry) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", entry.getEmail());
        values.put("username", entry.getUsername());
        values.put("photoUrl", entry.getPhotoUrl());
        values.put("likesCount", entry.getLikesCount());
        db.update("users", values, "id = " + entry.getId(), null);
    }

    public Profile getUserById(int u_id) {
        Profile user = null;
        db = dbHelper.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM users WHERE id = " + u_id, null);
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                user = formatUser(mCursor);
            } while (mCursor.moveToNext());
        }
        mCursor.close();
        return user;
    }

    public Profile getUserByUser(Profile entry) {
        Profile user = null;
        String where = " WHERE email = '" + entry.getEmail() + "'";
        db = dbHelper.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM users" + where, null);
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                user = formatUser(mCursor);
            } while (mCursor.moveToNext());
        }
        mCursor.close();
        return user;
    }

    public ArrayList<Profile> getUsers() {
        ArrayList<Profile> list = new ArrayList<Profile>();
        db = dbHelper.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM users", null);
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                Profile user = formatUser(mCursor);
                list.add(user);
            } while (mCursor.moveToNext());
        }
        mCursor.close();
        return list;
    }

    public void deleteUser(int user_id) {
        db = dbHelper.getWritableDatabase();
        db.delete("users", "user_id = " + user_id, null);
    }

    public Profile formatUser(Cursor mCursor) {
        Profile user = new Profile();
        user.setId(String.valueOf(mCursor.getInt(mCursor.getColumnIndex("id"))));
        user.setUsername(mCursor.getString(mCursor.getColumnIndex("username")));
        user.setPhotoUrl(mCursor.getString(mCursor.getColumnIndex("photoUrl")));
        user.setEmail(mCursor.getString(mCursor.getColumnIndex("email")));
        user.setLikesCount(mCursor.getLong(mCursor.getColumnIndex("likesCount")));
        return user;
    }
}
