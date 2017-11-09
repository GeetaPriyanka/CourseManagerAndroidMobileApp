package com.example.group19_hw06.Data;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by vikhy on 11/3/2017.
 */

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.group19_hw06.Model.Course;


/**
 * Created by Geeta Priyanka Janapareddy
 * Sai Vikhyat on 11/3/2017.
 */

public class CourseTable {
    public static final String TABLENAME = "Course";
    public static final String COLUMNTITLE = "title";
    public static final String COLUMNINSTRUCTOR = "instructor";
    public static final String COLUMNDAY = "day";
    public static final String COLUMNTIMEHOURS = "timeHours";
    public static final String COLUMNTIMEMINUTES = "timeMinutes";
    public static final String COLUMNPERIOD = "period";
    public static final String COLUMNCREDITHOURS = "creditHours";
    public static final String COLUMNSEMESTER = "semester";
    public static final String COLUMNCREATEDBY = "username";

    static public void onCreate(SQLiteDatabase db) {

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + TABLENAME + "(");
        sb.append(COLUMNTITLE + " TEXT ,  ");
        sb.append(COLUMNINSTRUCTOR + " TEXT , ");
        sb.append(COLUMNDAY + " TEXT , ");
        sb.append(COLUMNTIMEHOURS + " TEXT , ");
        sb.append(COLUMNTIMEMINUTES + " TEXT , ");
        sb.append(COLUMNPERIOD + " TEXT , ");
        sb.append(COLUMNCREDITHOURS + " INTEGER , ");
        sb.append(COLUMNSEMESTER + " TEXT , ");
        sb.append(COLUMNCREATEDBY + " TEXT );");
        try {
            db.execSQL(sb.toString());
        } catch (SQLException e) {

        }


    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        CourseTable.onCreate(db);
    }
}