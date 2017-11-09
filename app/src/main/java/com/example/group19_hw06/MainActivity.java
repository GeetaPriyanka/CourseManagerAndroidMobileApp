package com.example.group19_hw06;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.group19_hw06.Adapter.CourseListAdapter;
import com.example.group19_hw06.Adapter.InstructorListAdapter;
import com.example.group19_hw06.Fragment.AddInstructorFragment;
import com.example.group19_hw06.Fragment.CourseManagerFragment;
import com.example.group19_hw06.Fragment.CreateCourseFragment;
import com.example.group19_hw06.Fragment.DisplayCourseFragment;
import com.example.group19_hw06.Fragment.DisplayInstructorFragment;
import com.example.group19_hw06.Fragment.InstructorFragment;
import com.example.group19_hw06.Fragment.LoginFragment;
import com.example.group19_hw06.Fragment.SignUpFragment;
import com.example.group19_hw06.Model.Course;
import com.example.group19_hw06.Model.Instructor;

import java.util.ArrayList;
import java.util.function.IntToLongFunction;


/**
 * Created by Geeta Priyanka Janapareddy
 * Sai Vikhyat on 11/3/2017.
 */

public class MainActivity extends AppCompatActivity implements DisplayCourseFragment.OnFragmentInteractionListener, DisplayInstructorFragment.OnFragmentInteractionListener, CourseListAdapter.OnCourseSelect, InstructorListAdapter.OnInstructorSelected, LoginFragment.OnFragmentInteractionListener, SignUpFragment.OnFragmentInteractionListener, CourseManagerFragment.OnFragmentInteractionListener, CreateCourseFragment.OnFragmentInteractionListener, AddInstructorFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().add(R.id.container, new LoginFragment(), "first").commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void viewInstructor(Instructor instructor) {
        DisplayInstructorFragment displayInstructorFragment = new DisplayInstructorFragment();
        displayInstructorFragment.viewInstructor(instructor);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, displayInstructorFragment, "viewInstructor")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onTextChanged(ArrayList<Instructor> instructors) {

        InstructorFragment instructorFragment = new InstructorFragment();
        instructorFragment.onTextChanged(instructors);
        CourseManagerFragment courseManagerFragment = new CourseManagerFragment();
        courseManagerFragment.onTextChanged(instructors);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, courseManagerFragment, "AddInstructor")
                .addToBackStack(null)
                .commit();

    }


    @Override
    public void onCourseAdd(String courseTitle) {
        // Toast.makeText(this, courseTitle+ "added", Toast.LENGTH_SHORT).show();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new CourseManagerFragment(), "AddCourse")
                .addToBackStack(null)
                .commit();

    }


    @Override
    public void onInstructorChecked(int position) {
        this.position = position;


    }

    int position;

    public int getInstructorPosition() {
        return position;
    }

    @Override
    public void viewCourse(Course course) {


        DisplayCourseFragment displayCourseFragment = new DisplayCourseFragment();
        displayCourseFragment.setCourse(course);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, displayCourseFragment, "AddCourse")
                .addToBackStack(null)
                .commit();
    }
}
