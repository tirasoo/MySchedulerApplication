package com.example.myschedulerapplication.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myschedulerapplication.Database.Repository;
import com.example.myschedulerapplication.Entities.Assessment;
import com.example.myschedulerapplication.Entities.Course;
import com.example.myschedulerapplication.Entities.Term;
import com.example.myschedulerapplication.MainActivity;
import com.example.myschedulerapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Course Detail Activity.Shows the user the details of the selected course and the associated
 * instructor and assessments for the given course.
 */
public class DetailedCourseView extends AppCompatActivity {

    EditText courseName;
    EditText courseStart;
    EditText courseEnd;
    EditText editTextNotes;
    Spinner courseStatus;
    EditText instructorName;
    EditText instructorPhone;
    EditText email;
    String mCourseName;
    int termID;
    String startDate;
    String endDate;
    String status;
    String name;
    String phone;
    String courseEmail;
    Spinner termSpinner;
    Repository repository;
    Course currentCourse;
    int courseID;
    String notes;
    EditText courseNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Sets Edittext to IDs
        //Sets EditText to Course Variables
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        repository = new Repository(getApplication());
        mCourseName = getIntent().getStringExtra("courseTitle");
        courseName = findViewById(R.id.editcoursename);
        courseName.setText(mCourseName);
        startDate = getIntent().getStringExtra("courseStart");
        courseStart = findViewById(R.id.editcoursestart);
        courseStart.setText(startDate);
        endDate = getIntent().getStringExtra("courseEnd");
        courseEnd = findViewById(R.id.editcourseend);
        courseEnd.setText(endDate);
        name = getIntent().getStringExtra("instructorsName");
        instructorName = findViewById(R.id.instructornames);
        instructorName.setText(name);
        phone = getIntent().getStringExtra("instructorsPhone");
        instructorPhone = findViewById(R.id.instructorsphone);
        instructorPhone.setText(phone);
        notes = getIntent().getStringExtra("notes");
        courseNotes = findViewById(R.id.editTextNotes);
        courseNotes.setText(notes);
        courseEmail =getIntent().getStringExtra("email");
        email = findViewById(R.id.emailinstructor);
        email.setText(courseEmail);
        termID = getIntent().getIntExtra("termID",-1);
        courseID= getIntent().getIntExtra("courseID",-1);
        termSpinner =findViewById(R.id.termsspinner);
        //repository = new Repository(getApplication());
        List<Term>terms = repository.getAllTerms();
        ArrayAdapter<Term> termArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,terms);
        termSpinner.setAdapter (termArrayAdapter);
        if (termID ==-1)termID=1;
        int termArrayNumber =0;
            for (int i = 0;i<terms.size();i++){
                if (terms.get(i).getTermID()==termID)termArrayNumber=i;
        }
        termSpinner.setSelection(termArrayNumber);
        status = getIntent().getStringExtra("courseStatus");
        courseStatus = findViewById(R.id.coursestatusspinner);
        ArrayList<String> myStatus =new ArrayList<>();
        myStatus.add("Planning to take");
        myStatus.add("Completed");
        myStatus.add("Dropped");
        myStatus.add("In progress");
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,myStatus);
        courseStatus.setAdapter (statusAdapter);
        if (status ==null)status="Plan to take";
        switch (status){
            case "Planning to take":
                courseStatus.setSelection(0);
                break;
            case "Completed":
                courseStatus.setSelection(1);
                break;
            case "Dropped":
                courseStatus.setSelection(2);
                break;
            case "In Progress":
                courseStatus.setSelection(3);
                break;
        }
        //Set and Show the associated Assessments.
        repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.courserecyclerView);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> assessments = new ArrayList<>();
        for (Assessment assess : repository.getAllAssessments()) {
            if (assess.getCourseID() == courseID)
                assessments.add(assess);
        }
        assessmentAdapter.setAssessments(assessments);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton5);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailedCourseView.this, DetailedAssessmentView.class);
                intent.putExtra("courseId", courseID);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_details, menu);
        return true;
    }
    /**
     *
     * @param item The menu item that was selected. Course Information-enter/Add,edit and delete
     *             a course from the menu items that was selected.
     *
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            this.finish();
            return true;}
        /**
         *Save Course Button. Saves fields to Database and in the DetailedCourseView screen,navigation back to the CourseList Screen.
         */
        if(item.getItemId() == R.id.save) {
            Course course;
             if (courseID == -1) {
                 String cName = courseName.getText().toString();
                 String cStart = courseStart.getText().toString();
                 String cEnd = courseEnd.getText().toString();
                 String cInstructName = instructorName.getText().toString();
                 String cInstructPhone = instructorPhone.getText().toString();
                 String cNotes = courseNotes.getText().toString();
                 String cEmail = email.getText().toString();
                 course = new Course(0,cName,cStart,cEnd,status,cInstructName,cInstructPhone,cEmail,cNotes,termID);
                 repository.insert(course);
                 Intent intent=new Intent(DetailedCourseView.this,CoursesList.class);
                 intent.putExtra("termID", termID);
                 startActivity(intent);
             } else {
                 try{
                     course = new Course(courseID, courseName.getText().toString(),courseStart.getText().toString(),courseEnd.getText().toString(),status,instructorName.getText().toString(),instructorPhone.getText().toString(),email.getText().toString(),courseNotes.getText().toString(),termID);
                     repository.update(course);
                 Intent intent=new Intent(DetailedCourseView.this,CoursesList.class);
                 intent.putExtra("termID", termID);
                 startActivity(intent);}
                 catch (Exception e){
                     e.printStackTrace();
                 }
             }
             return true;
        }
        if(item.getItemId() == R.id.delete) {
            for (Course course : repository.getAllCourses()) {
                if (course.getTermID() == termID) currentCourse = course;
            }
                repository.delete(currentCourse);
                Toast.makeText(DetailedCourseView.this, "Course has been deleted successfully", Toast.LENGTH_LONG).show();
            return true;
        }
        // Optional notes addition and sharing feature per course
        if (item.getItemId()== R.id.sharenote) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, courseNotes.getText().toString() +"EXTRA_TEXT");
            sendIntent.putExtra(Intent.EXTRA_TITLE, "My Course Notes");
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }
        //Alerts for Courses
        if(item.getItemId()== R.id.alertstart) {
            String courseDateStart= courseStart.getText().toString();
            String courseTitle = courseName.getText().toString();
            String myDateFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myDateFormat, Locale.US);
            Date mydate = null;
            try {
                mydate = sdf.parse(courseDateStart);
            } catch (ParseException e){
                e.printStackTrace();
            }
            try {
            Long trigger = mydate.getTime();
            Intent intent1 = new Intent(DetailedCourseView.this , MyReceiver.class);
            intent1.putExtra("key", "Course " +  courseTitle + " Starts " + courseDateStart);
            PendingIntent sender = PendingIntent.getBroadcast(DetailedCourseView.this, ++MainActivity.numAlert,intent1, PendingIntent.FLAG_IMMUTABLE );
            AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger,sender);
            }
            catch (Exception e){
            }
            return true;
        }
        if(item.getItemId()== R.id.alertend) {
            String courseDateEnd= courseEnd.getText().toString();
            String courseTitle = courseName.getText().toString();
            String myDateFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myDateFormat, Locale.US);
            Date mydate = null;
            try {
                mydate = sdf.parse(courseDateEnd);
            } catch (ParseException e){
                e.printStackTrace();
            }
            try {
                Long trigger = mydate.getTime();
                Intent intent1 = new Intent(DetailedCourseView.this , MyReceiver.class);
                intent1.putExtra("key", "Course " +  courseTitle + " Ends " + courseDateEnd);
                PendingIntent sender = PendingIntent.getBroadcast(DetailedCourseView.this, ++MainActivity.numAlert,intent1, PendingIntent.FLAG_IMMUTABLE );
                AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger,sender);
            }
            catch (Exception e){
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * refreshes the recyclerView containing the assessments every time saving,modification is done
     */
    @Override
    protected void onResume() {
        super.onResume();
        //Assessments Recycler views
        RecyclerView recyclerView = findViewById(R.id.courserecyclerView);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> assessments = new ArrayList<>();
        for (Assessment a : repository.getAllAssessments()) {
            if (a.getCourseID() == courseID) assessments.add(a);
        }
        assessmentAdapter.setAssessments(assessments);
    }

}