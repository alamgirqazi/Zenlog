package io.github.alamgirqazi.zenlog;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alamgir on 12/31/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper{


    public static final String DATABASE_NAME="User.db";
    public static final String TABLE_NAME="UserInfo_table";
    public static final String COL_1="email";
    public static final String COL_2="name";
    public static final String COL_3="age";
    public static final String COL_4="city";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
//        SQLiteDatabase db = this.getWritableDatabase();
        
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(email TEXT, name TEXT, age INTEGER, city TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String email, String name, String age, String city)
    {
                SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,email);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,age);
        contentValues.put(COL_4,city);
       long result =  db.insert(TABLE_NAME,null,contentValues);

        if(result==-1)
        {
            return false;
        }
        else
            return true;
    }


}
