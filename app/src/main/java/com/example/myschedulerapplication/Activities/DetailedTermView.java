package com.example.myschedulerapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myschedulerapplication.Database.Repository;
import com.example.myschedulerapplication.Entities.Course;
import com.example.myschedulerapplication.Entities.Term;
import com.example.myschedulerapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;



/**
 * Term Detail Activity.Shows the user the details of selected term and associated courses.
 */

public class DetailedTermView extends AppCompatActivity {
    EditText editName;
    EditText editStart;
    EditText editEnd;
    /*EditText termName;
    EditText termStart;
    EditText termEnd;*/
    String mTermName;
    int termId;
    String startDate;
    String endDate;
    Term mTerm;
    Repository repository;
    Course mCourse;
    Term currentTerm;
    int numCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        mTermName = getIntent().getStringExtra("termName");
        editName = findViewById(R.id.editTextTermTitle);
        editName.setText(mTermName);

        startDate = getIntent().getStringExtra("termStart");
        editStart = findViewById(R.id.editTextTermStart);
        editStart.setText(startDate);

        endDate = getIntent().getStringExtra("termEnd");
        editEnd = findViewById(R.id.editTextTermEnd);
        editEnd.setText(endDate);
        termId = getIntent().getIntExtra("termID", -1);

        RecyclerView recyclerView = findViewById(R.id.courserecyclerView);
        repository = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> courses = new ArrayList<>();
        for (Course c :repository.getAllCourses()){
            if (c.getTermID() ==termId) courses.add(c);
        }
        courseAdapter.setCourses(courses);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailedTermView.this, DetailedCourseView.class);
                intent.putExtra("termID", termId);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_terms_details, menu);
        return true;
    }

    /**
     * This method handles action to take place based on the item selected
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            this.finish();
            return true;}
        if(item.getItemId()== R.id.termSave){
            Term term;
            if (termId == -1) {
                if (repository.getAllTerms().size() == 0) termId = 1;
                else
                    termId = repository.getAllTerms().get(repository.getAllTerms().size() - 1).getTermID() + 1;
                String tName = editName.getText().toString();
                String tStart = editStart.getText().toString();
                String tEnd = editEnd.getText().toString();
                term = new Term(termId, tName,tStart,tEnd);
                repository.insert(term);
                startActivity(new Intent(DetailedTermView.this,TermsList.class));
                finish();
            } else {
                try{
                    term = new Term(termId, editName.getText().toString(), editStart.getText().toString(),editEnd.getText().toString());
                    repository.update(term);}
                catch (Exception e){
                }
            }
            return true;
        }
        /**
         * Validation Implementation of term delete-cannot delete a term with courses in it.
         */
        if(item.getItemId()== R.id.termDelete) {
            //delete the term with ID "termId" from the repository,thro looping all of the terms in the repository and checks if it is equal to currentTerm
            for (Term term : repository.getAllTerms()) {
                if (term.getTermID() == termId) currentTerm = term;
            }
            numCourses = 0;
            for (Course course : repository.getAllCourses()) {
                if (course.getTermID() == termId) ++numCourses;
            }
            if (numCourses == 0) {
                repository.delete(currentTerm);
                Toast.makeText(DetailedTermView.this, currentTerm.getTermTitle() + " was deleted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(DetailedTermView.this, "Can't delete a term with course/s", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        if(item.getItemId()== R.id.termSave){
            if (termId == -1)
                Toast.makeText(DetailedTermView.this, "Please save term before adding courses", Toast.LENGTH_LONG).show();

            else {
                int courseID;
                if (repository.getAllCourses().size() == 0) courseID = 1;
                else
                    courseID = repository.getAllCourses().get(repository.getAllCourses().size() - 1).getCourseID() + 1;
                Course course = new Course(courseID, "C195", "07/28/2023","10/28/2023","Active","Caro","7633393569","tirasoo@gmail.com","You can do better in this course",termId);
                repository.insert(course);
                course = new Course(++courseID, "C196", "07/20/2023", "10/28/2023", "Active","Teba","7633393575","Caro@gmail.com","Doing Great",termId);
                repository.insert(course);
                RecyclerView recyclerView = findViewById(R.id.courserecyclerView);
                final CourseAdapter courseAdapter = new CourseAdapter(this);
                recyclerView.setAdapter(courseAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                List<Course> courses = new ArrayList<>();
                for (Course c : repository.getAllCourses()) {
                    if (c.getTermID() == termId) courses.add(c);
                }
                courseAdapter.setCourses(courses);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.courserecyclerView);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> courses = new ArrayList<>();
        for (Course c : repository.getAllCourses()) {
            if (c.getTermID() == termId) courses.add(c);
        }
        courseAdapter.setCourses(courses);
    }
}
