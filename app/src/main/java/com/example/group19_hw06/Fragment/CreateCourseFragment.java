package com.example.group19_hw06.Fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.group19_hw06.Adapter.InstructorListAdapter;
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
public class CreateCourseFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    int checkedInstructorPosition = 0;


    DataBaseDataManager dataBaseDataManager;
    CourseManagerFragment courseManagerFragment;
    String userName;

    Spinner semesterSpinner, daySpinner, periodSpinner;
    EditText editTextTitle, editTextHour, editTextMinute;
    RadioButton radioButton;
    RadioGroup radioGroup;


    private ArrayList<Instructor> instructors;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private RecyclerView.Adapter mAdapter;
    MainActivity activity;


    public CreateCourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.fragment_create_course, container, false);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        SharedPreferences prefs = getActivity().getSharedPreferences("loggedIn", MODE_PRIVATE);

        userName = prefs.getString("Username", "No name defined");


        final MainActivity activity = (MainActivity) getActivity();
        Toolbar myToolbar = (Toolbar) getView().findViewById(R.id.createCourse_toolbar);
        activity.setSupportActionBar(myToolbar);
        setHasOptionsMenu(true);
        dataBaseDataManager = new DataBaseDataManager(getActivity());

        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.createCourse_recyclerView);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        editTextTitle = (EditText) getActivity().findViewById(R.id.createCourse_title);

        editTextHour = (EditText) getActivity().findViewById(R.id.editTextHour);


        radioGroup = (RadioGroup) getActivity().findViewById(R.id.radioGroup_Credits);


        editTextMinute = (EditText) getActivity().findViewById(R.id.editTextMinute);


        instructors = dataBaseDataManager.getAllInstructors(userName);
        if (instructors.size() > 0) {
            mAdapter = new InstructorListAdapter(instructors, getActivity(), "CreateCourse");
            getActivity().findViewById(R.id.textViewNoInstructors).setVisibility(View.GONE);
            mRecyclerView.setAdapter(mAdapter);
            getActivity().findViewById(R.id.textViewNoInstructors).setVisibility(View.INVISIBLE);


        } else {

            getActivity().findViewById(R.id.buttonCreate).setEnabled(false);
            Log.d("demo", "No instructors");
        }

        semesterSpinner = (Spinner) getActivity().findViewById(R.id.semester_spinner);
        daySpinner = (Spinner) getActivity().findViewById(R.id.day_spinner);
        periodSpinner = (Spinner) getActivity().findViewById(R.id.period_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.semester_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semesterSpinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),
                R.array.days_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
                R.array.period_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        periodSpinner.setAdapter(adapter2);


        getActivity().findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
                builder.setTitle("Reset fields")
                        .setMessage("Are you sure you want to continue?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editTextTitle.setText("");
                                editTextMinute.setText("");
                                editTextHour.setText("");
                                semesterSpinner.setSelected(false);
                                daySpinner.setSelected(false);
                                radioGroup.setSelected(false);

                                Toast.makeText(getActivity(), "All fields have been reset", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });


        getActivity().findViewById(R.id.buttonCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String hour = editTextHour.getText().toString();
                String minute = editTextMinute.getText().toString();
                String semester = semesterSpinner.getSelectedItem().toString();
                radioButton = (RadioButton) getActivity().findViewById(radioGroup.getCheckedRadioButtonId());

                if (!hour.equals("") && !minute.equals("") && !editTextTitle.getText().toString().trim().equals("")
                        && semesterSpinner.getSelectedItem() != null && radioGroup.getCheckedRadioButtonId() != -1) {
                    String period = periodSpinner.getSelectedItem().toString();
                    if (Integer.parseInt(hour) < 1) {
                        Toast.makeText(getActivity(), "Hour should be greater than = 1 and less than = 12 ", Toast.LENGTH_SHORT).show();
                    } else if (Integer.parseInt(hour) > 12) {
                        Toast.makeText(getActivity(), "Hour should be greater than = 1 and less than= 12 ", Toast.LENGTH_SHORT).show();
                    } else if (Integer.parseInt(minute) < 0) {
                        Toast.makeText(getActivity(), "Minutes should be between 0 and 59", Toast.LENGTH_SHORT).show();

                    } else if (Integer.parseInt(minute) > 59) {
                        Toast.makeText(getActivity(), "Minutes should be between 0 and 59", Toast.LENGTH_SHORT).show();
                    } else {
                        Course course = new Course();
                        int creditHours = Integer.parseInt(radioButton.getText().toString());

                        String title = editTextTitle.getText().toString();
                        String day = daySpinner.getSelectedItem().toString();

                        course.setTitle(title);
                        course.setInstructor(null);
                        course.setDay(day);
                        course.setSemester(semester);
                        course.setCreditHours(creditHours);
                        course.setPeriod(period);
                        course.setTimeHours(hour);
                        course.setTimeMinutes(minute);
                        course.setUsername(userName);
                        Instructor instructor = dataBaseDataManager.getInstructor(instructors.get(activity.getInstructorPosition()).getEmail(), userName);

                        course.setInstructor(instructor.getFirstName() + " " + instructor.getLastName());


                        dataBaseDataManager.saveCourse(course);
                        // courseList.add(course);
                        Toast.makeText(getActivity(), "Course added", Toast.LENGTH_SHORT).show();


                        ((MainActivity) getActivity()).onCourseAdd(course.getTitle());
                    }


                } else {
                    Toast.makeText(activity, "Input fields", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onCourseAdd(String courseTitle);
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
                ArrayList<Instructor> instructorArrayList = dataBaseDataManager.getAllInstructors(userName);
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