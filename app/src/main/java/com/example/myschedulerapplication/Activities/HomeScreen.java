package com.example.myschedulerapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myschedulerapplication.R;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        //put the buttons on the onCreate method where everything happens when the app runs
        // instance variables/fields that connect our buttons
        Button allTerms = findViewById(R.id.allTerms);
        Button allCourses = findViewById(R.id.allCourses);
        Button allAssessments = findViewById(R.id.allAssessments);

        allTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add code to transfer navigation to Term activity.
                Intent intent = new Intent(HomeScreen.this, TermsList.class);
                startActivity(intent);
            }
        });
        allCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add code to transfer navigation to Term activity.
                Intent intent = new Intent(HomeScreen.this, CoursesList.class);
                startActivity(intent);
            }
        });
        allAssessments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add code to transfer navigation to Term activity.
                Intent intent = new Intent(HomeScreen.this, AssessmentsList.class);
                startActivity(intent);
            }
        });
    }
}