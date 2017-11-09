package com.example.group19_hw06.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.group19_hw06.Data.DataBaseDataManager;
import com.example.group19_hw06.MainActivity;
import com.example.group19_hw06.Model.User;
import com.example.group19_hw06.R;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Geeta Priyanka Janapareddy
 * Sai Vikhyat on 11/3/2017.
 */

public class LoginFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    EditText username, password;
    DataBaseDataManager dm;


    public LoginFragment() {
        // Required empty public constructor

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        Toolbar myToolbar = (Toolbar) getView().findViewById(R.id.home_toolbar);
        activity.setSupportActionBar(myToolbar);
        setHasOptionsMenu(true);
        dm = new DataBaseDataManager(getActivity());

        SharedPreferences prefs = getActivity().getSharedPreferences("loggedIn", MODE_PRIVATE);

        String name = prefs.getString("Username", "No name defined");//"No name defined" is the default value.


        if (dm.getUser(name) != null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new CourseManagerFragment(), "LoginFragment")
                    .addToBackStack(null)
                    .commit();

        }


        getActivity().findViewById(R.id.signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new SignUpFragment(), "Second")
                        .addToBackStack(null)
                        .commit();
            }
        });

        getActivity().findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = (EditText) getView().findViewById(R.id.home_username);
                password = (EditText) getView().findViewById(R.id.home_password);
                User user = dm.getUser(username.getText().toString());
                if (user != null && user.getPassword().equals(password.getText().toString())) {


                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("loggedIn", MODE_PRIVATE).edit();
                    editor.putString("Username", username.getText().toString());
                    editor.apply();

                    //    user.setLoggedIn(true);

                    //CourseManagerFragment courseManagerFragment=new CourseManagerFragment();
                    // courseManagerFragment.onUserChanged(user.getUserName());
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, new CourseManagerFragment(), "Third")
                            .addToBackStack(null)
                            .commit();
                } else
                    Toast.makeText(getActivity(), "The username and password provided is not registered or invalid. Please click on signup to register", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
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
                Toast.makeText(getActivity(), "Log-in to continue", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_add_instructor:
                Toast.makeText(getActivity(), "Log-in to continue", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_logout:
                return true;
            case R.id.action_exit:
                getActivity().finishAffinity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
