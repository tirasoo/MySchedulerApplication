package com.example.myschedulerapplication.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myschedulerapplication.DAO.AssessmentDAO;
import com.example.myschedulerapplication.DAO.CoursesDAO;
import com.example.myschedulerapplication.DAO.TermDAO;
import com.example.myschedulerapplication.Entities.Assessment;
import com.example.myschedulerapplication.Entities.Course;
import com.example.myschedulerapplication.Entities.Term;

/**
 *Student Scheduler DBase Builder. Sets pre-determined Terms,courses, and assessments.
 */
@Database(entities = {Term.class,Assessment.class,Course.class},version = 2,exportSchema = false)
public abstract class StudentSchedulerDbaseBuilder extends RoomDatabase{
    public abstract CoursesDAO coursesDAO();
    public abstract TermDAO termDao();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile StudentSchedulerDbaseBuilder INSTANCE;

    static StudentSchedulerDbaseBuilder getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (StudentSchedulerDbaseBuilder.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),StudentSchedulerDbaseBuilder.class,"StudentSchedulerDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
