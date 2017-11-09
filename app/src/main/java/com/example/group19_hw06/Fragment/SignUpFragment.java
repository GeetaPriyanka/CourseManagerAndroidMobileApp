package com.example.group19_hw06.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.group19_hw06.R;

import com.example.group19_hw06.Model.User;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Geeta Priyanka Janapareddy
 * Sai Vikhyat on 11/3/2017.
 */
public class SignUpFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    EditText firstname, lastname, username, password;
    private static final int CAMERA_REQUEST = 1888;
    DataBaseDataManager dm;
    DbBitmapUtility dbBitmap;
    ImageView imageView;
    private static final int SELECT_PHOTO = 100;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        Toolbar myToolbar = (Toolbar) getView().findViewById(R.id.signup_toolbar);
        activity.setSupportActionBar(myToolbar);
        setHasOptionsMenu(true);

        imageView = (ImageView) getView().findViewById(R.id.imageView);
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

        dm = new DataBaseDataManager(getActivity());
        dbBitmap = new DbBitmapUtility();

        getActivity().findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                firstname = (EditText) getView().findViewById(R.id.signup_firstname);
                lastname = (EditText) getView().findViewById(R.id.signup_lastname);
                username = (EditText) getView().findViewById(R.id.signup_username);
                password = (EditText) getView().findViewById(R.id.signup_password);

                imageView = (ImageView) getView().findViewById(R.id.imageView);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                });


                if (firstname.getText().toString().isEmpty() == true) {
                    Toast.makeText(getActivity(), "Please provide first name", Toast.LENGTH_LONG).show();

                } else if (username.getText().toString().equals(password.getText().toString()))

                    Toast.makeText(getActivity(), "Password cannot be the same as Username", Toast.LENGTH_SHORT).show();

                else if (lastname.getText().toString().isEmpty() == true) {
                    Toast.makeText(getActivity(), "Please provide last name", Toast.LENGTH_LONG).show();

                } else if (username.getText().toString().isEmpty() == true) {
                    Toast.makeText(getActivity(), "Please provide username", Toast.LENGTH_LONG).show();

                } else if (dm.getUser(username.getText().toString()) != null) {
                    Toast.makeText(getActivity(), "The username already exists. Please provide another username", Toast.LENGTH_LONG).show();

                } else if (password.getText().toString().isEmpty() == true) {
                    Toast.makeText(getActivity(), "Please provide a password", Toast.LENGTH_LONG).show();
                } else if (password.getText().length() < 8) {
                    Toast.makeText(getActivity(), "Please provide password of minimum 8 characters", Toast.LENGTH_LONG).show();

                } else {

                    User user = new User();
                    imageView.buildDrawingCache();
                    Bitmap bmap = imageView.getDrawingCache();

                    byte[] image = dbBitmap.getBytes(bmap);

                    user.setFirstName(firstname.getText().toString());
                    user.setLastName(lastname.getText().toString());
                    user.setPassword(password.getText().toString());
                    user.setUserName(username.getText().toString());
                    user.setImage(image);
                    dm.saveUser(user);

                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("loggedIn", MODE_PRIVATE).edit();
                    editor.putString("Username", user.getUserName());
                    editor.apply();

                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, new CourseManagerFragment(), "Third")
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
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
                getFragmentManager().beginTransaction().replace(R.id.container, new LoginFragment(), "first").commit();
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
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
