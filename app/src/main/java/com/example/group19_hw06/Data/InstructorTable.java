package com.example.group19_hw06.Data;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.group19_hw06.Data.UserTable;
import com.example.group19_hw06.Model.Instructor;

/**
 * Created by Geeta Priyanka Janapareddy
 * Sai Vikhyat on 11/3/2017.
 */

public class InstructorTable {
    public static final String TABLENAME = "Instructor";
    public static final String COLUMNFIRSTNAME = "firstname";
    public static final String COLUMNLASTNAME = "lastname";
    public static final String COLUMNEMAIL = "email";
    public static final String COLUMNPERSONALWEBSITE = "personalWebsite";
    public static final String COLUMNIMAGE = "image";
    public static final String COLUMNUSERNAME = "username";


    static public void onCreate(SQLiteDatabase db) {

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + TABLENAME + "(");
        sb.append(COLUMNEMAIL + " TEXT PRIMARY KEY,  ");

        sb.append(COLUMNFIRSTNAME + " TEXT NOT NULL, ");
        sb.append(COLUMNLASTNAME + " TEXT NOT NULL, ");
        sb.append(COLUMNPERSONALWEBSITE + " TEXT NOT NULL, ");
        sb.append(COLUMNIMAGE + " BLOB,  ");
        sb.append(COLUMNUSERNAME + " TEXT ) ; ");

        try {
            db.execSQL(sb.toString());
        } catch (SQLException e) {

        }
    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //if(oldVersion<newVersion)
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        InstructorTable.onCreate(db);
    }

}
