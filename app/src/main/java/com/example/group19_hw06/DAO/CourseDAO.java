package com.example.group19_hw06.DAO;

/**
 * Created by Geeta Priyanka Janapareddy
 * Sai Vikhyat on 11/3/2017.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.group19_hw06.Data.CourseTable;
import com.example.group19_hw06.Model.Course;

import java.util.ArrayList;
import java.util.List;

import static java.util.jar.Pack200.Unpacker.TRUE;


public class CourseDAO {
    private SQLiteDatabase db;


    public CourseDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(Course course) {
        ContentValues values = new ContentValues();
        values.put(CourseTable.COLUMNTITLE, course.getTitle());
        values.put(CourseTable.COLUMNINSTRUCTOR, course.getInstructor());
        values.put(CourseTable.COLUMNDAY, course.getDay());
        values.put(CourseTable.COLUMNTIMEHOURS, course.getTimeHours());
        values.put(CourseTable.COLUMNTIMEMINUTES, course.getTimeMinutes());
        values.put(CourseTable.COLUMNPERIOD, course.getPeriod());
        values.put(CourseTable.COLUMNCREDITHOURS, course.getCreditHours());
        values.put(CourseTable.COLUMNSEMESTER, course.getSemester());
        values.put(CourseTable.COLUMNCREATEDBY, course.getUsername());


        return db.insert(CourseTable.TABLENAME, null, values);

    }


    public boolean delete(Course course, String userName) {
        return db.delete(CourseTable.TABLENAME, CourseTable.COLUMNTITLE + "=?" + " AND " + CourseTable.COLUMNCREATEDBY + " = ? ", new String[]{course.getTitle(), userName}) > 0;
    }

    public Course get(String title, String userName) {
        Course course = null;

        Cursor c = db.query(true, CourseTable.TABLENAME, new String[]{CourseTable.COLUMNTITLE, CourseTable.COLUMNINSTRUCTOR, CourseTable.COLUMNDAY, CourseTable.COLUMNTIMEHOURS, CourseTable.COLUMNTIMEMINUTES, CourseTable.COLUMNPERIOD}
                , CourseTable.COLUMNTITLE + "=?" + " AND " + CourseTable.COLUMNCREATEDBY + " = ? ", new String[]{title, userName}, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            course = buildFilterFromCursor(c);
            if (!c.isClosed()) {
                c.close();
            }
        }
        return course;
    }


    public List<Course> getAll(String userName) {

        List<Course> courseList = new ArrayList<Course>();
        String whereClause = CourseTable.COLUMNCREATEDBY + " = ? ";
        String[] whereArgs = new String[]{
                userName
        };
        //  "Column1 =? AND Column2 =?"

        Cursor c = db.query(CourseTable.TABLENAME, (new String[]{CourseTable.COLUMNTITLE
                        , CourseTable.COLUMNINSTRUCTOR, CourseTable.COLUMNDAY
                        , CourseTable.COLUMNTIMEHOURS, CourseTable.COLUMNTIMEMINUTES
                        , CourseTable.COLUMNPERIOD, String.valueOf(CourseTable.COLUMNCREDITHOURS)
                        , CourseTable.COLUMNSEMESTER, CourseTable.COLUMNCREATEDBY})
                ,
                whereClause,
                whereArgs, null, null, null);
        if (c != null && c.moveToFirst()) {
            do {
                Course course = buildFilterFromCursor(c);
                if (course != null) {
                    courseList.add(course);
                }

            }
            while (c.moveToNext());
            if (!c.isClosed()) {
                c.close();
            }
        }
        return courseList;
    }

    private Course buildFilterFromCursor(Cursor c) {
        Course course = null;

        if (c != null)
            course = new Course(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getInt(6), c.getString(7), c.getString(8));

        return course;
    }

}
