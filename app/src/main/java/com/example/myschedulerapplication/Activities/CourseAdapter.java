package com.example.myschedulerapplication.Activities;

import  android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myschedulerapplication.Entities.Course;
import com.example.myschedulerapplication.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView coursesItemView;
        private CourseViewHolder(View itemView) {
            super(itemView);
            coursesItemView = itemView.findViewById(R.id.textView5);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, DetailedCourseView.class);
                    intent.putExtra("courseTitle", current.getCourseTitle());
                    intent.putExtra("courseStart", current.getStartDate());
                    intent.putExtra("courseEnd", current.getEndDate());
                    intent.putExtra("courseStatus", current.getStatus());
                    intent.putExtra("instructorsName", current.getInstructorName());
                    intent.putExtra("instructorsPhone", current.getInstructorPhone());
                    intent.putExtra("email", current.getInstructorEmail());
                    intent.putExtra("notes",current.getNotes());
                    intent.putExtra("courseID",current.getCourseID());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }


    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.courses_list_item, parent, false);
        return new CourseAdapter.CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        if (mCourses != null) {
            Course current = mCourses.get(position);
            String courseTitle = current.getCourseTitle();
            holder.coursesItemView.setText(courseTitle);
        }

        else {
            holder.coursesItemView.setText("No Course Added.Add a Course Pliz");
        }
    }

    /**
     *
     * @ determines the number of Items--> how many rows to put in the recyclerView.
     */

    @Override
    public int getItemCount() {
        return mCourses.size();
    }


    public void setCourses(List<Course> allCourses) {
        mCourses=allCourses;
        notifyDataSetChanged();
    }

}
