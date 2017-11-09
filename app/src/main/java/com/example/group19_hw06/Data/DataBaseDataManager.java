package com.example.group19_hw06.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.example.group19_hw06.DAO.CourseDAO;
import com.example.group19_hw06.DAO.InstructorDAO;
import com.example.group19_hw06.DAO.UserDAO;
import com.example.group19_hw06.Model.Course;
import com.example.group19_hw06.Model.Instructor;
import com.example.group19_hw06.Model.User;


/**
 * Created by Geeta Priyanka Janapareddy
 * Sai Vikhyat on 11/3/2017.
 */

public class DataBaseDataManager {
    private Context context;
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase db;
    private UserDAO userDAO;
    private CourseDAO courseDAO;

    private InstructorDAO instructorDAO;

    public DataBaseDataManager(Context context) {
        this.context = context;
        dataBaseHelper = new DataBaseHelper(this.context);
        db = dataBaseHelper.getWritableDatabase();
        userDAO = new UserDAO(db);
        instructorDAO = new InstructorDAO(db);
        courseDAO = new CourseDAO(db);
    }

    public void close() {
        if (db != null) {
            db.close();
        }
    }

    public long saveCourse(Course course) {
        return courseDAO.save(course);
    }

    public UserDAO getFilterDAO() {
        return this.userDAO;
    }

    public long saveUser(User user) {
        return userDAO.save(user);
    }

    public User getUser(String username) {
        return userDAO.get(username);
    }

    public Instructor getInstructor(String email, String userName) {
        return instructorDAO.get(email, userName);
    }

    public Course getCourse(String title, String userName) {
        return courseDAO.get(title, userName);
    }


    public List<User> getAllUsers() {
        return userDAO.getAll();
    }

    public List<Course> getAllCourses(String userName) {
        return courseDAO.getAll(userName);
    }

    public long saveInstructor(Instructor instructor) {
        return instructorDAO.save(instructor);
    }

    public boolean deleteInstructor(Instructor instructor) {
        return instructorDAO.delete(instructor);
    }

    public boolean deleteCourse(Course course, String userName) {
        return courseDAO.delete(course, userName);
    }

    public ArrayList<Instructor> getAllInstructors(String userName) {
        return instructorDAO.getAll(userName);
    }
}


