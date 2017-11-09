

/**
 * Created by Geeta Priyanka Janapareddy
 * Sai Vikhyat on 11/3/2017.
 */
package com.example.group19_hw06.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group19_hw06.Data.DataBaseDataManager;
import com.example.group19_hw06.DbBitmapUtility;
import com.example.group19_hw06.Model.Course;
import com.example.group19_hw06.Model.Instructor;
import com.example.group19_hw06.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class InstructorListAdapter extends RecyclerView.Adapter<InstructorListAdapter.ViewHolder> {

    private static ArrayList<Instructor> mData;
    private static Context mContext;
    private static int pos;
    private DbBitmapUtility dbBitmapUtility;
    private int lastChecked = 0;
    private DataBaseDataManager dm;
    OnInstructorSelected onInstructorSelected;
    String usage;

    //0
    public InstructorListAdapter(ArrayList<Instructor> mData, Context context, String usage) {
        this.mData = mData;
        mContext = context;
        onInstructorSelected = (OnInstructorSelected) context;
        this.usage = usage;

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    //2
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.instructor, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //3
    int x;

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Instructor instructor = mData.get(position);

        dm = new DataBaseDataManager(mContext);

        if (instructor.getImage() != null) {
            Bitmap photo = dbBitmapUtility.getImage(instructor.getImage());
            holder.image.setImageBitmap(photo);

        }
        holder.radioButton.setChecked(position == lastChecked);
        holder.textViewEmail.setText(instructor.getEmail());
        holder.textViewName.setText(instructor.getFirstName());
        holder.instructor = instructor;

        if (usage.equals("Instructor"))
            holder.radioButton.setVisibility(View.INVISIBLE);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onInstructorSelected.viewInstructor(mData.get(position));
            }
        });

        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
                builder.setTitle("Delete instructor")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SharedPreferences prefs = mContext.getSharedPreferences("loggedIn", MODE_PRIVATE);
                                String userName = prefs.getString("Username", "No name defined");//"No name defined" is the default value.
                                int flag=1;
                                ArrayList<Course> arrayList=new ArrayList<>();
                                arrayList=(ArrayList)dm.getAllCourses(userName);
                                for(int i=0;i<arrayList.size();i++)
                                    if(arrayList.get(i).getInstructor().equals(mData.get(position).getFirstName()+" "+mData.get(position).getLastName()))
                                            flag=-1;

                                if(flag==1) {
                                    dm.deleteInstructor(mData.get(position));
                                    mData.remove(position);
                                    notifyDataSetChanged();
                                    Toast.makeText(mContext, "Instructor deleted", Toast.LENGTH_SHORT).show();
                                }
                                else
                                    Toast.makeText(mContext, "Instructor associated,can't be deleted", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();

                return false;
            }
        });
    }

    //1
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewEmail;
        ImageView image;
        RadioButton radioButton;
        LinearLayout linearLayout;

        Instructor instructor;

        public ViewHolder(final View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.instructor_name);
            textViewEmail = (TextView) itemView.findViewById(R.id.instructor_email);
            image = (ImageView) itemView.findViewById(R.id.instructor_photo);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout_instructor);
            radioButton = (RadioButton) itemView.findViewById(R.id.radioButtonSelect);

            pos = getAdapterPosition();

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastChecked = getAdapterPosition();
                    notifyDataSetChanged();
                    onInstructorSelected.onInstructorChecked(lastChecked);

                }
            });


        }


    }

    public interface OnInstructorSelected {
        void onInstructorChecked(int position);

        void viewInstructor(Instructor instructor);
    }


}