package com.example.group19_hw06.Adapter;


/**
 * Created by Geeta Priyanka Janapareddy
 * Sai Vikhyat on 11/3/2017.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.example.group19_hw06.Data.DataBaseDataManager;
import com.example.group19_hw06.DbBitmapUtility;
import com.example.group19_hw06.MainActivity;
import com.example.group19_hw06.Model.Course;
import com.example.group19_hw06.R;

import java.util.ArrayList;


public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {

    public static ArrayList<Course> courses;
    public static Context mContext;
    private DataBaseDataManager dm;
    public static int pos;
    String userName;
    OnCourseSelect onCourseSelect;

    //0
    public CourseListAdapter(ArrayList<Course> courses, Context context) {
        this.courses = courses;
        mContext = context;
        dm = new DataBaseDataManager(mContext);
        onCourseSelect = (MainActivity) context;


    }


    @Override
    public int getItemCount() {
        return courses.size();
    }

    //2
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    //3
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Course course = courses.get(position);

        userName = course.getUsername();
        /*if (course.getImage() != null) {
            Bitmap photo = dbBitmapUtility.getImage(instructor.getImage());
            holder.image.setImageBitmap(photo);

        }*/
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCourseSelect.viewCourse(courses.get(position));

            }
        });
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
                builder.setTitle("Delete course")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                dm.deleteCourse(courses.get(position), userName);
                                courses.remove(position);
                                notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();


                return true;
            }
        });

        holder.textViewTitle.setText(course.getTitle());
        holder.textViewInstructor.setText(course.getInstructor());
        holder.textViewHour.setText(course.getTimeHours() + ":");
        holder.textViewMinute.setText(course.getTimeMinutes());
        holder.textViewPeriod.setText(course.getPeriod());
        holder.textViewDay.setText(course.getDay());

        holder.course = course;
    }

    //1
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewInstructor, textViewMinute, textViewHour, textViewPeriod, textViewDay;

        Course course;
        LinearLayout linearLayout;

        public ViewHolder(final View itemView) {
            super(itemView);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    return false;
                }
            });

            textViewTitle = (TextView) itemView.findViewById(R.id.course_title);
            textViewInstructor = (TextView) itemView.findViewById(R.id.course_instructor);
            textViewInstructor = (TextView) itemView.findViewById(R.id.course_instructor);
            textViewDay = (TextView) itemView.findViewById(R.id.course_day);
            textViewHour = (TextView) itemView.findViewById(R.id.course_hour);
            textViewMinute = (TextView) itemView.findViewById(R.id.course_minute);
            textViewPeriod = (TextView) itemView.findViewById(R.id.course_period);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.cList_container);


            pos = getAdapterPosition();


        }


    }

    public interface OnCourseSelect {
        void viewCourse(Course course);
    }


}
