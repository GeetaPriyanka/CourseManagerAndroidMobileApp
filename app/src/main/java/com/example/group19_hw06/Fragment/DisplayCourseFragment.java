package com.example.group19_hw06.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.group19_hw06.Data.DataBaseDataManager;
import com.example.group19_hw06.MainActivity;
import com.example.group19_hw06.Model.Course;
import com.example.group19_hw06.Model.Instructor;
import com.example.group19_hw06.R;

import java.util.ArrayList;

/**
 * Created by Geeta Priyanka Janapareddy
 * Sai Vikhyat on 11/3/2017.
 */

import static android.content.Context.MODE_PRIVATE;


public class DisplayCourseFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    Course course;
    DataBaseDataManager dm;
    String userName;
    MainActivity activity;

    public DisplayCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (MainActivity) getActivity();

        Toolbar myToolbar = (Toolbar) getView().findViewById(R.id.displayCourse_toolbar);
        activity.setSupportActionBar(myToolbar);
        setHasOptionsMenu(true);


        SharedPreferences prefs = getActivity().getSharedPreferences("loggedIn", MODE_PRIVATE);
        userName = prefs.getString("Username", "No name defined");
        dm=new DataBaseDataManager(getActivity());


        TextView textViewTitle = (TextView) getActivity().findViewById(R.id.textViewCtitle);
        TextView textViewInstructor = (TextView) getActivity().findViewById(R.id.textViewCinstructor);
        TextView textViewDay = (TextView) getActivity().findViewById(R.id.textViewCday);
        TextView textViewCreditHours = (TextView) getActivity().findViewById(R.id.textViewCcreditHours);
        TextView textViewSemester = (TextView) getActivity().findViewById(R.id.textViewCsemester);
        TextView textViewTime = (TextView) getActivity().findViewById(R.id.textViewCtime);

        textViewTitle.setText(course.getTitle());
        textViewDay.setText("Every " + course.getDay());
        textViewCreditHours.setText(course.getCreditHours() + " credited");
        textViewSemester.setText(course.getSemester() + " semester ");
        textViewTime.setText(course.getTimeHours() + ":" + course.getTimeMinutes() + " " + course.getPeriod());
        textViewInstructor.setText(course.getInstructor());
    }

    public void setCourse(Course course) {
        this.course = course;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_course, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                getFragmentManager().beginTransaction().replace(R.id.container, new CourseManagerFragment(), "first").commit();

                return true;
            case R.id.action_instructors:

                SharedPreferences prefs = getActivity().getSharedPreferences("loggedIn", MODE_PRIVATE);
                userName = prefs.getString("Username", "No name defined");//"No name defined" is the default value.
                ArrayList<Instructor> instructorArrayList = dm.getAllInstructors(userName);
                InstructorFragment instructorFragment = new InstructorFragment();
                instructorFragment.onTextChanged(instructorArrayList);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, instructorFragment, "AddInstructor")
                        .addToBackStack(null)
                        .commit();
                return true;

            case R.id.action_add_instructor:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new AddInstructorFragment(), "AddInstructor")
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.action_logout:
                SharedPreferences pref = getActivity().getSharedPreferences("loggedIn", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("Username");
                getFragmentManager().beginTransaction().replace(R.id.container, new LoginFragment(), "first").commit();
                editor.commit();
                return true;
            case R.id.action_exit:
                getActivity().finishAffinity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
