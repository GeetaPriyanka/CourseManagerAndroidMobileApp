package com.example.group19_hw06.Fragment;

import android.app.Fragment;
import android.content.SharedPreferences;
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

import com.example.group19_hw06.Adapter.InstructorListAdapter;
import com.example.group19_hw06.Data.DataBaseDataManager;
import com.example.group19_hw06.MainActivity;
import com.example.group19_hw06.Model.Instructor;
import com.example.group19_hw06.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Geeta Priyanka Janapareddy
 * Sai Vikhyat on 11/3/2017.
 */
public class InstructorFragment extends Fragment {

    // private OnFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    DataBaseDataManager dm;
    ArrayList<Instructor> instructors = new ArrayList<>();
    String userName;

    public InstructorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_instructor, container, false);
    }


    public void onTextChanged(ArrayList<Instructor> value) {

        instructors = new ArrayList<>();
        instructors = value;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dm = new DataBaseDataManager(getActivity());
        MainActivity activity = (MainActivity) getActivity();
        Toolbar myToolbar = (Toolbar) getView().findViewById(R.id.instructorFragment_toolbar);
        activity.setSupportActionBar(myToolbar);
        setHasOptionsMenu(true);

        SharedPreferences prefs = getActivity().getSharedPreferences("loggedIn", MODE_PRIVATE);

        userName = prefs.getString("Username", "No name defined");


        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.instructor_recyclerview);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if(instructors==null)
            instructors=dm.getAllInstructors(userName);
        if (instructors != null)
            mAdapter = new InstructorListAdapter(instructors, getActivity(), "Instructor");


        mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
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
                /*ArrayList<Instructor> instructorArrayList = dm.getAllInstructors(userName);
                InstructorFragment instructorFragment = new InstructorFragment();
                instructorFragment.onTextChanged(instructorArrayList);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, instructorFragment, "AddInstructor")
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

}
