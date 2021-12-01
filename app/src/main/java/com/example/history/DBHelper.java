package com.example.history;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE history (_id INTEGER PRIMARY KEY AUTOINCREMENT,"+"path TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oidVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS history");
        onCreate(db);
    }
}
