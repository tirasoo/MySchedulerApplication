package com.example.myschedulerapplication.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myschedulerapplication.Entities.Assessment;

import java.util.List;

/**
 *Assessment DAO Interface. Holds SQL statements for database.
 */
@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("DELETE FROM assessments_table")
    void deleteAllTerms();

    @Query("SELECT * FROM assessments_table ORDER BY assessmentID ASC")
    List<Assessment> getAllAssessments();

}
