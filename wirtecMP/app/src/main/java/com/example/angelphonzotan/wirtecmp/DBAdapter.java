package com.example.angelphonzotan.wirtecmp;

/**
 * Created by Anjoh on 13/03/2018.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.SyncStateContract;
public class DBAdapter {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "hallOfFame";
    public static final String TB_NAME = "Users";
    public static final String COLUMN_ID = "User";
    public static final String COLUMN_NAME1 = "Name";
    public static final String COLUMN_NAME2 = "Time";

    Context c;
    SQLiteDatabase db;
    MyDBHandler helper;

    public DBAdapter(Context c) {
        this.c = c;
        helper=new MyDBHandler(c);
    }

    //OPEN DB
    public void openDB()
    {
        try
        {
            db=helper.getWritableDatabase();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //CLOSE
    public void closeDB()
    {
        try
        {
            helper.close();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //INSERT DATA
    public boolean add(User u)
    {
        try
        {
            ContentValues cv=new ContentValues();
            cv.put("name", u.getName());
            cv.put("time",u.getTime());

            db.insert(TB_NAME, "id", cv);

            return true;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    //RETRIEVE DATA AND FILTER
    public Cursor retrieve(String searchTerm)
    {
        String[] columns={"name","time"};
        Cursor c=null;

        if(searchTerm != null && searchTerm.length()>0)
        {
            String sql="SELECT * FROM "+TB_NAME+" WHERE Name LIKE '%"+searchTerm+"%'";
            c=db.rawQuery(sql,null);
            return c;

        }

        c=db.query(TB_NAME,columns,null,null,null,null,null);
        return c;
    }
}
