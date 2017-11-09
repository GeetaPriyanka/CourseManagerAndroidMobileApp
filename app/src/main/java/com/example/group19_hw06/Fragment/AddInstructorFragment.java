package com.example.group19_hw06.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.group19_hw06.Data.DataBaseDataManager;
import com.example.group19_hw06.DbBitmapUtility;
import com.example.group19_hw06.MainActivity;
import com.example.group19_hw06.Model.Instructor;
import com.example.group19_hw06.R;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.transform.Result;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Geeta Priyanka Janapareddy
 * Sai Vikhyat on 11/3/2017.
 */
public class AddInstructorFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private static final int CAMERA_REQUEST = 1888;
    ImageView imageView;
    DbBitmapUtility dbBitmapUtility;
    Instructor instructor;
    byte[] image;
    String userName;
    EditText firstName, lastName, email, personalWebsite;
    private static final int SELECT_PHOTO = 100;


    private ArrayList<Instructor> instructors = new ArrayList<>();
    DataBaseDataManager dm;
    InstructorFragment instructorFragment;

    public AddInstructorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_instructor, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();

        dm = new DataBaseDataManager(getActivity());

        SharedPreferences prefs = getActivity().getSharedPreferences("loggedIn", MODE_PRIVATE);
        userName = prefs.getString("Username", "No name defined");//"No name defined" is the default value.


        Toolbar myToolbar = (Toolbar) getView().findViewById(R.id.addInstructor_toolbar);
        activity.setSupportActionBar(myToolbar);
        setHasOptionsMenu(true);


        firstName = (EditText) getActivity().findViewById(R.id.addInstructor_firstname);
        lastName = (EditText) getActivity().findViewById(R.id.addInstructor_lastname);
        email = (EditText) getActivity().findViewById(R.id.addInstructor_email);
        personalWebsite = (EditText) getActivity().findViewById(R.id.addInstructor_personalWebsite);


        imageView = (ImageView) getActivity().findViewById(R.id.addInstructor_imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence options[] = new CharSequence[]{"Camera", "Gallery"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true);
                builder.setTitle("Select");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, CAMERA_REQUEST);
                        } else if (which == 1) {
                            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                            photoPickerIntent.setType("image/*");
                            startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            }
        });

        getActivity().findViewById(R.id.addInstructor_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
                builder.setTitle("Reset fields")
                        .setMessage("Are you sure you want to continue?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                firstName.setText("");
                                lastName.setText("");
                                email.setText("");
                                personalWebsite.setText("");
                                imageView.setImageResource(R.drawable.add_photo);
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


        getActivity().findViewById(R.id.addInstructor_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView = (ImageView) getActivity().findViewById(R.id.addInstructor_imageView);
                imageView.buildDrawingCache();

                Bitmap bmap = imageView.getDrawingCache();

                image = dbBitmapUtility.getBytes(bmap);


                instructor = new Instructor(firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), personalWebsite.getText().toString(), image);

                instructor.setUsername(userName);
                int flag = 0;

                ArrayList<Instructor> instructorArrayList = dm.getAllInstructors(userName);
                for (int i = 0; i < instructorArrayList.size(); i++) {
                    if (instructor.getEmail().equals(instructorArrayList.get(i).getEmail()))
                        flag = 1;
                }
                if (flag == 0) {
                    instructors.add(instructor);
                    dm.saveInstructor(instructor);
                    Toast.makeText(getActivity(), "Instructor added", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), "Instructor already exists", Toast.LENGTH_SHORT).show();


                if (instructors != null & flag == 0) {
                    ((MainActivity) getActivity()).onTextChanged(instructors);

                    //instructorFragment = new InstructorFragment();
                    //mListener.onTextChanged(instructors);
                }


            }
        });


    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onTextChanged(ArrayList<Instructor> instructors);
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
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new CourseManagerFragment(), "AddInstructor")
                        .addToBackStack(null)
                        .commit();
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        } else if (requestCode == SELECT_PHOTO) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                BufferedInputStream bufferedInputStream = new BufferedInputStream(imageStream);
                Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);
                imageView.setImageBitmap(bmp);
                // Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
            }
        }
        //Bitmap photo = (Bitmap) data.getExtras().get("data");


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

}
