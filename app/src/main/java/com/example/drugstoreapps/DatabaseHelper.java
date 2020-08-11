package com.example.drugstoreapps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "db_drug.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) { super(context, DATABASE_NAME, null,DATABASE_VERSION);}
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql = "CREATE TABLE drug(id integer PRIMARY KEY AUTOINCREMENT, code text NULL, name text NULL, price text NULL, pack text NULL);"; // kode, nama, harga, kemasan
        Log.d("Data","onCreate: "+sql);
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2){

    }

}
