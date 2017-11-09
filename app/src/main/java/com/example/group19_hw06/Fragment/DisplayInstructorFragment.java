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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.group19_hw06.Data.DataBaseDataManager;
import com.example.group19_hw06.DbBitmapUtility;
import com.example.group19_hw06.MainActivity;
import com.example.group19_hw06.Model.Instructor;
import com.example.group19_hw06.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Geeta Priyanka Janapareddy
 * Sai Vikhyat on 11/3/2017.
 */
public class DisplayInstructorFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    MainActivity activity;
    Instructor instructor;
    DbBitmapUtility dbBitmapUtility;
    DataBaseDataManager dm;

    public DisplayInstructorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_instructor, container, false);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toolbar myToolbar = (Toolbar) getView().findViewById(R.id.displayInstructor_toolbar);
        dm=new DataBaseDataManager(getActivity());
        activity.setSupportActionBar(myToolbar);
        setHasOptionsMenu(true);
        TextView textViewFN = (TextView) getActivity().findViewById(R.id.displayInstructorFN);
        TextView textViewLN = (TextView) getActivity().findViewById(R.id.displayInstructorLN);
        TextView textViewEmail = (TextView) getActivity().findViewById(R.id.displayInstructorEmail);
        TextView textViewWebsite = (TextView) getActivity().findViewById(R.id.displayInstructorWebsite);
        ImageView imageView = (ImageView) getActivity().findViewById(R.id.imageView2);


        textViewFN.setText(instructor.getFirstName());
        textViewLN.setText(instructor.getLastName());
        textViewEmail.setText(instructor.getEmail());
        textViewWebsite.setText(instructor.getPersonalWebsite());

        imageView.setImageBitmap(dbBitmapUtility.getImage(instructor.getImage()));

    }

    public void viewInstructor(Instructor instructor) {
        this.instructor = instructor;

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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
                String userName = prefs.getString("Username", "No name defined");//"No name defined" is the default value.

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
}
