package com.example.akula.messagebox.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper  extends SQLiteOpenHelper{

    public static final String DATABASE_NAME ="todo.db";
    public static final int DATABASE_VERSION = 1;

    public DbHelper(Context context ){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContractClass.ReminderStorage.CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ContractClass.ReminderStorage.DELETE_QUERY);
        onCreate(db);
    }
}
