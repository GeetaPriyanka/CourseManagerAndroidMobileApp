package com.example.group19_hw06.DAO;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.group19_hw06.Data.CourseTable;
import com.example.group19_hw06.Data.InstructorTable;
import com.example.group19_hw06.Model.Instructor;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Geeta Priyanka Janapareddy
 * Sai Vikhyat on 11/3/2017.
 */

public class InstructorDAO {
    private SQLiteDatabase db;


    public InstructorDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(Instructor instructor) {
        ContentValues values = new ContentValues();
        values.put(InstructorTable.COLUMNFIRSTNAME, instructor.getFirstName());
        values.put(InstructorTable.COLUMNLASTNAME, instructor.getLastName());
        values.put(InstructorTable.COLUMNEMAIL, instructor.getEmail());
        values.put(InstructorTable.COLUMNPERSONALWEBSITE, instructor.getPersonalWebsite());
        values.put(InstructorTable.COLUMNIMAGE, instructor.getImage());
        values.put(InstructorTable.COLUMNUSERNAME, instructor.getUsername());


        return db.insert(InstructorTable.TABLENAME, null, values);
    }


    public boolean delete(Instructor instructor) {


        return db.delete(InstructorTable.TABLENAME, InstructorTable.COLUMNEMAIL + " =? " + " AND " + InstructorTable.COLUMNUSERNAME + " =? ", new String[]{instructor.getEmail(), instructor.getUsername()}) > 0;
    }

    public Instructor get(String email, String userName) {
        Instructor instructor = null;

        Cursor c = db.query(true, InstructorTable.TABLENAME, new String[]{InstructorTable.COLUMNEMAIL
                        , InstructorTable.COLUMNFIRSTNAME, InstructorTable.COLUMNLASTNAME
                        , InstructorTable.COLUMNPERSONALWEBSITE, InstructorTable.COLUMNIMAGE
                        , InstructorTable.COLUMNUSERNAME}
                , InstructorTable.COLUMNEMAIL + " =? " + " AND " + InstructorTable.COLUMNUSERNAME + " =? ", new String[]{email, userName}, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            instructor = buildFilterFromCursor(c);
            if (!c.isClosed()) {
                c.close();
            }
        }
        return instructor;
    }

    private String userName;

    public ArrayList<Instructor> getAll(String userName) {

        userName = userName;
        ArrayList<Instructor> instructorList = new ArrayList<Instructor>();
        String whereClause = CourseTable.COLUMNCREATEDBY + " =? ";
        String[] whereArgs = new String[]{
                userName
        };
        Cursor c = db.query(InstructorTable.TABLENAME, new String[]{InstructorTable.COLUMNEMAIL, InstructorTable.COLUMNFIRSTNAME, InstructorTable.COLUMNLASTNAME,
                        InstructorTable.COLUMNPERSONALWEBSITE, InstructorTable.COLUMNIMAGE, InstructorTable.COLUMNUSERNAME}
                , whereClause, whereArgs, null, null, null);
        if (c != null && c.moveToFirst()) {
            do {
                Instructor instructor = buildFilterFromCursor(c);
                if (instructor != null) {
                    instructorList.add(instructor);
                }

            }
            while (c.moveToNext());
            if (!c.isClosed()) {
                c.close();
            }
        }
        return instructorList;
    }

    private Instructor buildFilterFromCursor(Cursor c) {
        Instructor instructor = null;

        if (c != null) {
            instructor = new Instructor(c.getString(1), c.getString(2), c.getString(0), c.getString(3), c.getBlob(4));
            instructor.setUsername(c.getString(5));
        }

        return instructor;
    }

}
