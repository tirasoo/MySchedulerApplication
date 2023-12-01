package com.example.myschedulerapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myschedulerapplication.Activities.HomeScreen;
import com.example.myschedulerapplication.Database.Repository;
import com.example.myschedulerapplication.Entities.Assessment;
import com.example.myschedulerapplication.Entities.Course;
import com.example.myschedulerapplication.Entities.Term;


public class MainActivity extends AppCompatActivity {
    //private Repository repository;
    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=findViewById(R.id.loginButton);
        Repository repo = new Repository(getApplication());
       Term term = new Term(1, "Term 1", "10/31/2023", "11/11/2023");
        repo.insert(term);
        term = new Term(2, "Term 2", "10/31/2023", "12/31/2023");
        repo.insert(term);
//        Repository repository = new Repository(getApplication());
        Course course = new Course(1 , "C195", "07/28/2023","10/28/2023","Active","Carol","7633393569","tirasoo@gmail.com","You can do better in this course",2);
        repo.insert(course);
        course = new Course(2 , "C176", "07/28/2023","10/28/2023","Completed","Bryan","7633393575","brayo@gmail.com","Good job in this course",1);
        repo.insert(course);
        Assessment assessment = new Assessment(1 , "Performance Assessment", "C196 Quiz","10/25/2023","12/25/2023",2);
        repo.insert(assessment);
        assessment = new Assessment(2 , "Objective Assessment", "C176 OA","05/25/2023","12/25/2023",1);
        repo.insert(assessment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, HomeScreen.class);
                startActivity(intent);
            }
        });
    }
}