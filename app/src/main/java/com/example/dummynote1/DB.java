package com.example.dummynote1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

/**
 * Created by Samuel on 2017/11/7.
 */

public class DB {
    private static final String DATABASE_NAME = "note.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE = "note";
    //    private static final String NEW_DATABASE_TABLE = "mytable2";
    private static final String DATABASE_CREATE =
            "CREATE TABLE IF NOT EXISTS note(_id INTEGER PRIMARY KEY,note TEXT,created INTEGER);";
//    private static final String NEW_DATABASE_CREATE =
//            "CREATE TABLE IF NOT EXISTS mytable2(_id INTEGER PRIMARY KEY,note TEXT,created INTEGER);";

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }//建立資料表

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
//            db.execSQL("DROP TABLE IF EXISTS " + NEW_DATABASE_TABLE);
//            onCreate(db);
        }

    }

    private Context mCtx = null;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public DB(Context ctx) {
        this.mCtx = ctx;
    }

    public DB open() throws SQLException {
        dbHelper = new DatabaseHelper(mCtx);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    private static final String KEY_ROWID = "_id";
    private static final String KEY_NOTE = "note";
    private static final String KEY_CREATED = "created";
    String[] strCols = new String[]{
            KEY_ROWID, KEY_NOTE, KEY_CREATED
    };

//    public void close() {
//        dbHelper.close();
//    }

    public Cursor getAll() {
//        return  db.rawQuery("SELECT * FROM "+DATABASE_TABLE,null);
        return db.query(DATABASE_TABLE, strCols, null, null, null, null, null);
    }

    public long create(String noteName) {
        Date now = new Date();
        ContentValues args = new ContentValues();
        args.put(KEY_NOTE, noteName);
        args.put(KEY_CREATED, now.toString());
        return db.insert(DATABASE_TABLE, null, args);
    }

    public boolean delete(long rowId) {
        if(rowId>0)
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
        else
            return db.delete(DATABASE_TABLE,null,null) > 0;
    }

    public boolean deleteAll() {
        return delete(-1);
    }

    public boolean update(long rowId, String note) {
        ContentValues args = new ContentValues();
        args.put(KEY_NOTE, note);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
