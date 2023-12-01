package com.example.myschedulerapplication.Database;

import android.app.Application;

import com.example.myschedulerapplication.DAO.AssessmentDAO;
import com.example.myschedulerapplication.DAO.CoursesDAO;
import com.example.myschedulerapplication.DAO.TermDAO;
import com.example.myschedulerapplication.Entities.Assessment;
import com.example.myschedulerapplication.Entities.Course;
import com.example.myschedulerapplication.Entities.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Repository --> A file to add/create,delete and edit objects.
 */
public class Repository {
    private final TermDAO mTermDAO;
    private final CoursesDAO mCourseDAO;
    private final AssessmentDAO mAssessmentDAO;
    private List<Term>mAllTerm;
    private List<Course>mAllCourse;
    private List<Assessment>mAllAssessment;

    private static final int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        StudentSchedulerDbaseBuilder db = StudentSchedulerDbaseBuilder.getDatabase(application);
        mAssessmentDAO = db.assessmentDAO();
        mCourseDAO = db.coursesDAO();
        mTermDAO =db.termDao();
    }
    /**
     *
     * Get all terms
     */
    public List<Term> getAllTerms(){
        databaseExecutor.execute(()->{
            mAllTerm= mTermDAO.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerm;
    }
    /**
     *Insert Term in DB.
     */
    public void insert(Term term){
        databaseExecutor.execute(()->{
            mTermDAO.insert(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     *Update Term in DB.
     */
    public void update(Term term){
        databaseExecutor.execute(()->{
            mTermDAO.update(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     *Delete Term in DB.
     */
    public void delete(Term term){
        databaseExecutor.execute(()->{
            mTermDAO.delete(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     *
     * Get all courses
     */
    public List<Course> getAllCourses(){
        databaseExecutor.execute(()->{
            mAllCourse= mCourseDAO.getAllCourses();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourse;
    }
    /**
     *Insert Course in DB.
     */

    public void insert(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.insert(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     *Delete Course in DB.
     */
    public void update(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.update(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     *Delete Course in DB.
     */
    public void delete(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.delete(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     *
     * Get all assessments
     */
    public List<Assessment> getAllAssessments(){
        databaseExecutor.execute(()->{
            mAllAssessment= mAssessmentDAO.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessment;
    }
    public void insert(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.insert(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.update(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.delete(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
