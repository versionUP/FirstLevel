package com.example.akula.messagebox.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class TodoProvider  extends ContentProvider{


    private DbHelper mDbHelper;
    public static final int CODE_TODO = 100;
    public static final int CODE_TODO_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ContractClass.CONTENT_AUTHORITY;


        /* This URI is content://com.example.todo/todo/ */
        matcher.addURI(authority, ContractClass.ReminderStorage.TABLE_NAME,CODE_TODO);

        /*
         * This URI would look something like content://com.example.todo/todo/1
         * The "/#" signifies to the UriMatcher that if TABLE_NAME is followed by ANY number,
         * that it should return the CODE_TODO_WITH_ID code
         */
        matcher.addURI(authority, ContractClass.ReminderStorage.TABLE_NAME +"/#",CODE_TODO_WITH_ID);
        return matcher;
    }




    @Override
    public boolean onCreate() {
        mDbHelper = new DbHelper(getContext());
        return mDbHelper !=null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)){
            case CODE_TODO:
                long _id = db.insert(ContractClass.ReminderStorage.TABLE_NAME, null, contentValues);

                /* if _id is equal to -1 insertion failed */
                if (_id != -1 && getContext() != null) {

                    /*
                     * This will help to broadcast that database has been changed,
                     * and will inform entities to perform automatic update.
                     */
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return ContractClass.ReminderStorage.buildTodoWithId(_id);
            default:
                return null;
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
