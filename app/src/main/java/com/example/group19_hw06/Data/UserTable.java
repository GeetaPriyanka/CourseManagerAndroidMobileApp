package com.example.group19_hw06.Data;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Geeta Priyanka Janapareddy
 * Sai Vikhyat on 11/3/2017.
 */

public class UserTable {
    public static final String TABLENAME = "User";
    public static final String COLUMNFIRSTNAME = "firstname";
    public static final String COLUMNLASTNAME = "lastname";
    public static final String COLUMNUSERNAME = "username";
    public static final String COLUMNPASSWORD = "password";
    public static final String COLUMNIMAGE = "image";

    static public void onCreate(SQLiteDatabase db) {

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + TABLENAME + "(");
        sb.append(COLUMNUSERNAME + " TEXT PRIMARY KEY,  ");
        sb.append(COLUMNFIRSTNAME + " TEXT NOT NULL, ");
        sb.append(COLUMNLASTNAME + " TEXT NOT NULL, ");
        sb.append(COLUMNPASSWORD + " TEXT NOT NULL, ");
        sb.append(COLUMNIMAGE + " BLOB );");
        try {
            db.execSQL(sb.toString());
        } catch (SQLException e) {
            Log.d("demo", "dfds");
            Log.d("demo", "dfds");


        }


    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        UserTable.onCreate(db);
    }
}

