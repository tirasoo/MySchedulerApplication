package com.example.myschedulerapplication.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myschedulerapplication.Entities.Course;

import java.util.List;

/**
 *Course  DAO Interface. Holds SQL statements for database.
 */
@Dao
public interface CoursesDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("DELETE FROM courses_Table")
    void deleteAllTerms();


    @Query("SELECT * FROM courses_Table ORDER BY courseID ASC")
    List<Course> getAllCourses();
}
