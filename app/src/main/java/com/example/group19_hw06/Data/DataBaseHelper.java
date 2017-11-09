package com.example.group19_hw06.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Geeta Priyanka Janapareddy
 * Sai Vikhyat on 11/3/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "courseManager.db";
    static final int DB_VERSION = 17;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        UserTable.onCreate(db);
        InstructorTable.onCreate(db);
        CourseTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        UserTable.onUpgrade(db, oldVersion, newVersion);
        InstructorTable.onUpgrade(db, oldVersion, newVersion);
        CourseTable.onUpgrade(db, oldVersion, newVersion);
    }
}
