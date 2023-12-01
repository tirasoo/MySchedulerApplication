package com.example.myschedulerapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myschedulerapplication.Database.Repository;
import com.example.myschedulerapplication.Entities.Course;
import com.example.myschedulerapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 termAdapter.setTerms(allTerms);
 * CourseList class--->Main Course screen,and Contains list of all the Courses.
 */
public class CoursesList extends AppCompatActivity {
    private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CoursesList.this, DetailedCourseView.class);
                startActivity(intent);
            }
        });
        repository = new Repository(getApplication());
        List<Course> allCourses = repository.getAllCourses();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(allCourses);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_details, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        if (item.getItemId() == R.id.save) {
            Repository repo = new Repository(getApplication());
            Course course = new Course(1, "Mobile App Development", "12/31/2023", "11/11/2024","Completed","Greig","123456","tirasoo123456@gmail.org","Incomplete Assignments",2);
            repo.insert(course);
            course = new Course(2, "Java Essentials", "10/31/2023", "12/31/2023","Active","Pascal","0722876399","jakom001@gmail.com","Success",1);
            repo.insert(course);
            List<Course> allCourses= repository.getAllCourses();
            RecyclerView recyclerView = findViewById(R.id.recyclerview);
            final CourseAdapter courseAdapter = new CourseAdapter(this);
            recyclerView.setAdapter(courseAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            courseAdapter.setCourses(allCourses);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * the method refreshes the current page/activity-the courseslist- after saving a new course to it.To reflect
     * the new course on the courselist activity.
     */
    @Override
    protected void onResume() {
        super.onResume();
        List<Course> allCourses=repository.getAllCourses();
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        final CourseAdapter courseAdapter=new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(allCourses);
    }
}



