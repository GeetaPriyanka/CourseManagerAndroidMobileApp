package com.example.group19_hw06.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.example.group19_hw06.Model.User;
import com.example.group19_hw06.Data.UserTable;


/**
 * Created by Geeta Priyanka Janapareddy
 * Sai Vikhyat on 11/3/2017.
 */

public class UserDAO {

    private SQLiteDatabase db;

    public UserDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(User user) {
        ContentValues values = new ContentValues();
        values.put(UserTable.COLUMNFIRSTNAME, user.getFirstName());
        values.put(UserTable.COLUMNLASTNAME, user.getLastName());
        values.put(UserTable.COLUMNUSERNAME, user.getUserName());
        values.put(UserTable.COLUMNPASSWORD, user.getPassword());
        values.put(UserTable.COLUMNIMAGE, user.getImage());


        return db.insert(UserTable.TABLENAME, null, values);
    }

    public boolean update(User user) {
        ContentValues values = new ContentValues();
        values.put(UserTable.COLUMNFIRSTNAME, user.getFirstName());
        values.put(UserTable.COLUMNLASTNAME, user.getLastName());
        values.put(UserTable.COLUMNUSERNAME, user.getUserName());
        values.put(UserTable.COLUMNPASSWORD, user.getPassword());
        values.put(UserTable.COLUMNIMAGE, user.getImage());

        return db.update(UserTable.TABLENAME, values, UserTable.COLUMNUSERNAME + "=?", new String[]{user.getUserName()}) > 0;
    }

    public boolean delete(User user) {
        return db.delete(UserTable.TABLENAME, UserTable.COLUMNUSERNAME + "=?", new String[]{user.getUserName()}) > 0;
    }

    public User get(String username) {
        User user = null;

        Cursor c = db.query(true, UserTable.TABLENAME, new String[]{UserTable.COLUMNUSERNAME, UserTable.COLUMNFIRSTNAME, UserTable.COLUMNLASTNAME, UserTable.COLUMNPASSWORD, UserTable.COLUMNIMAGE}
                , UserTable.COLUMNUSERNAME + "=?", new String[]{username}, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            user = buildFilterFromCursor(c);
            if (!c.isClosed()) {
                c.close();
            }
        }
        return user;
    }

    public User getPassword(String password) {
        User user = null;

        Cursor c = db.query(true, UserTable.TABLENAME, new String[]{UserTable.COLUMNUSERNAME, UserTable.COLUMNFIRSTNAME, UserTable.COLUMNLASTNAME, UserTable.COLUMNPASSWORD, UserTable.COLUMNIMAGE}
                , UserTable.COLUMNPASSWORD + "=?", new String[]{password}, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            user = buildFilterFromCursor(c);
            if (!c.isClosed()) {
                c.close();
            }
        }
        return user;
    }


    public List<User> getAll() {

        List<User> usersList = new ArrayList<User>();
        Cursor c = db.query(UserTable.TABLENAME, new String[]{UserTable.COLUMNUSERNAME, UserTable.COLUMNFIRSTNAME, UserTable.COLUMNLASTNAME,
                        UserTable.COLUMNPASSWORD, UserTable.COLUMNIMAGE}
                , null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            do {
                User user = buildFilterFromCursor(c);
                if (user != null) {
                    usersList.add(user);
                }

            }
            while (c.moveToNext());
            if (!c.isClosed()) {
                c.close();
            }
        }
        return usersList;
    }

    private User buildFilterFromCursor(Cursor c) {
        User user = null;

        if (c != null) {
            user = new User();

            user.setUserName(c.getString(0));
            user.setFirstName(c.getString(1));
            user.setLastName(c.getString(2));
            user.setPassword(c.getString(3));
            user.setImage(c.getBlob(4));
        }

        return user;
    }
}
