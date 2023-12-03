package com.example.myschedulerapplication.Activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myschedulerapplication.Database.Repository;
import com.example.myschedulerapplication.Entities.Assessment;
import com.example.myschedulerapplication.MainActivity;
import com.example.myschedulerapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailedAssessmentView extends AppCompatActivity {
    Repository repository;
    int courseID;
    int numCourses;
    int assessmentID;
    List<Assessment> allAssessments;
    RecyclerView recyclerView;
    Assessment assessment;
    EditText editType;
    EditText editTitle;
    EditText editStart;
    EditText editEnd;
    String assessType;
    String assessTitle;
    String assessStart;
    String assessEnd;

    TextView editDate;
    Assessment currentAssessment;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    String assessmentTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        //repository = new Repository(getApplication());
        assessTitle = getIntent().getStringExtra("assessmentTitle");
        editTitle = findViewById(R.id.assessmentTitle);
        editTitle.setText(assessTitle);
        assessType = getIntent().getStringExtra("assessmentType");
        editType = findViewById(R.id.assessmentType);
        editType.setText(assessType);
        assessStart = getIntent().getStringExtra("assessmentStartDate");
        editStart = findViewById(R.id.assstartdate);
        editStart.setText(assessStart);
        assessEnd = getIntent().getStringExtra("assessmentEndDate");
        editEnd = findViewById(R.id.assenddate);
        editEnd.setText(assessEnd);
        assessmentID = getIntent().getIntExtra("assessmentID", -1);
        courseID = getIntent().getIntExtra("courseID", -1);
        repository = new Repository(getApplication());
        allAssessments = repository.getAllAssessments();
        for (Assessment a : allAssessments) {
            if (a.getAssessmentID() == assessmentID) assessment = a;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_details, menu);
        return true;
    }
    /**
     * This method handles action to take place based on the item selected
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        if (item.getItemId() == R.id.asssave) {
            Assessment assessment;
            if (assessmentID == -1) {
                assessment = new Assessment(0, editTitle.getText().toString(), editType.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), courseID);
                repository.insert(assessment);
                Intent intent=new Intent(DetailedAssessmentView.this, AssessmentsList.class);
                intent.putExtra("courseID", courseID);
                startActivity(intent);
            } else {
                try{
                assessment = new Assessment(assessmentID, editTitle.getText().toString(), editType.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), courseID);
                repository.update(assessment);
                Intent intent=new Intent(DetailedAssessmentView.this, AssessmentsList.class);
                intent.putExtra("courseID", courseID);
                startActivity(intent);}
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            return true;
        }
        if(item.getItemId()== R.id.deleteassessment){
            for (Assessment assessment : repository.getAllAssessments()) {
                if (assessment.getCourseID() == courseID) currentAssessment = assessment;
            }
            repository.delete(currentAssessment);
            Toast.makeText(DetailedAssessmentView.this, "Assessment has been deleted successfully", Toast.LENGTH_LONG).show();
            return true;
        }

        if(item.getItemId()== R.id.alertstart) {
            String assessmentDateStart= editStart.getText().toString();
            String assessmentTitle = editTitle.getText().toString();
            String dateFormat = "MM/dd/yy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
            Date date = null;
            try {
                date=simpleDateFormat.parse(assessmentDateStart);
            } catch (ParseException e){
                e.printStackTrace();
            }
            Long trig = date.getTime();
            Intent intent1 = new Intent(DetailedAssessmentView.this , MyReceiver.class);
            intent1.putExtra("key", assessmentTitle + " STARTS TODAY " + assessmentDateStart);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(DetailedAssessmentView.this, ++MainActivity.numAlert,intent1,PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trig,pendingIntent);
            return true;
        }

        if(item.getItemId()== R.id.alertend){
            String assessmentDateEnd= editEnd.getText().toString();
            assessmentTitle = editTitle.getText().toString();
            String dateFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
            Date date = null;
            try {
                date=sdf.parse(assessmentDateEnd);
            } catch (ParseException e){
                e.printStackTrace();
            }
            Long trig = date.getTime();
            Intent intent1 = new Intent(DetailedAssessmentView.this, MyReceiver.class);
            intent1.putExtra("key", assessmentTitle +" ENDS TODAY "+ assessmentDateEnd);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(DetailedAssessmentView.this, ++MainActivity.numAlert, intent1, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trig,pendingIntent);
            return true;
        }
            return super.onOptionsItemSelected(item);
    }
}




