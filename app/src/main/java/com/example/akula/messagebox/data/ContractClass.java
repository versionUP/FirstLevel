package com.example.akula.messagebox.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class ContractClass {

    //To query a content provider, you specify the query string in the form of a URI which has following format
    // content://content_authority/table_name
    public static final String CONTENT_AUTHORITY = "com.example.akula.messagebox";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

   public static final class ReminderStorage implements BaseColumns{


       public static final String TABLE_NAME ="reminders";

       public static final String COLUMN_DATE ="date";
       public static final String COLUMN_TASK ="task";
       public static final String COLUMN_STATUS ="status";


       public  static final String CREATE_QUERY = "CREATE TABLE "+TABLE_NAME + " (" +
               _ID + " INTEGER PRIMARY KEY, " +
               COLUMN_DATE + " INTEGER NOT NULL, "+
               COLUMN_STATUS + " TEXT,"+
               COLUMN_TASK + " TEXT" +
               ")";

       public static final String DELETE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;





       /* The base CONTENT_URI used to query the  table from the content provider */
       public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_NAME)
                .build();

       /**
        * Builds a URI that adds the task _ID to the end of the todo content URI path.
        * This is used to query details about a single todo entry by _ID. This is what we
        * use for the detail view query.
        *
        * @param id Unique id pointing to that row
        * @return Uri to query details about a single todo entry
        */

       public static Uri buildTodoWithId (long id){
           return CONTENT_URI.buildUpon()
                             .appendPath(Long.toString(id))
                             .build();
       }


   }
}
