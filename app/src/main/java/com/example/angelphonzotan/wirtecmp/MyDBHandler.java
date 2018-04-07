package com.example.angelphonzotan.wirtecmp;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by Anjoh on 12/03/2018.
 */

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "hallOfFame";
    public static final String TABLE_NAME = "Users";
    public static final String COLUMN_ID = "User";
    public static final String COLUMN_NAME1 = "Name";
    public static final String COLUMN_NAME2 = "Time";

    public MyDBHandler(Context context) {
        super(context, DB_NAME, null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try
        {
            db.execSQL(TABLE_NAME);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    //public User findHandler(String studentname) {}
    //public boolean deleteHandler(int ID) {}
    //public boolean updateHandler(int ID, String name) {}
}
