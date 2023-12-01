package com.example.myschedulerapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myschedulerapplication.Database.Repository;
import com.example.myschedulerapplication.Entities.Assessment;
import com.example.myschedulerapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * AssessmentList class--->Main Assessment screen,and contains list of all assessments.
 */
public class AssessmentsList extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton4);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(AssessmentsList.this, DetailedAssessmentView.class);
            startActivity(intent);
        });
        repository = new Repository(getApplication());
        List<Assessment> allAssessments = repository.getAllAssessments();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(allAssessments);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        if (item.getItemId() == R.id.asssave) {
            Repository repo = new Repository(getApplication());
            Assessment assessment = new Assessment(1, "Objective Assessment", "Java Essentials", "11/11/2023", "12/11/2023", 2);
            repo.insert(assessment);
            assessment = new Assessment(2, "Performance Assessment", "Calculus 2", "12/31/2023", "01/31/2024", 1);
            repo.insert(assessment);
            List<Assessment> allAssessments = repository.getAllAssessments();
            RecyclerView recyclerView = findViewById(R.id.recyclerview);
            final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
            recyclerView.setAdapter(assessmentAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            assessmentAdapter.setAssessments(allAssessments);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        List<Assessment> allAssessments = repository.getAllAssessments();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(allAssessments);
    }
}







