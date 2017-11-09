package com.example.group19_hw06.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.group19_hw06.Adapter.CourseListAdapter;
import com.example.group19_hw06.Data.DataBaseDataManager;
import com.example.group19_hw06.MainActivity;
import com.example.group19_hw06.Model.Course;
import com.example.group19_hw06.Model.Instructor;
import com.example.group19_hw06.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Geeta Priyanka Janapareddy
 * Sai Vikhyat on 11/3/2017.
 */

public class CourseManagerFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private DataBaseDataManager dm;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    String userName;

    private ArrayList<Course> courses;
    private RecyclerView.Adapter mAdapter;
    ArrayList<Instructor> instructors = new ArrayList<>();


    public CourseManagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.manager_course, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dm = new DataBaseDataManager(getActivity());
        MainActivity activity = (MainActivity) getActivity();
        Toolbar myToolbar = (Toolbar) getView().findViewById(R.id.manager_course_toolbar);
        activity.setSupportActionBar(myToolbar);
        setHasOptionsMenu(true);

        SharedPreferences prefs = getActivity().getSharedPreferences("loggedIn", MODE_PRIVATE);
        userName = prefs.getString("Username", "No name defined");//"No name defined" is the default value.


        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.managerCourse_recyclerView);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        courses = (ArrayList) dm.getAllCourses(userName);


        if (courses != null) {
            mAdapter = new CourseListAdapter(courses, getActivity());
            getActivity().findViewById(R.id.textViewNoCourses).setVisibility(View.GONE);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

        }


        getActivity().findViewById(R.id.imageButtonAddNewCourse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new CreateCourseFragment(), "CreateCourse")
                        .addToBackStack(null)
                        .commit();
            }
        });

    }


    public void onTextChanged(ArrayList<Instructor> value) {

        instructors = new ArrayList<>();
        instructors = value;

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

                return true;
            case R.id.action_instructors:
                ArrayList<Instructor> instructorArrayList = dm.getAllInstructors(userName);
                InstructorFragment instructorFragment = new InstructorFragment();
                instructorFragment.onTextChanged(instructorArrayList);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, instructorFragment, "AddInstructor")
                        .addToBackStack(null)
                        .commit();

                /*InstructorFragment instructorFragment=new InstructorFragment();
                instructorFragment.onTextChanged(instructors);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, instructorFragment, "Instructor")
                        .addToBackStack(null)
                        .commit();*/
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
