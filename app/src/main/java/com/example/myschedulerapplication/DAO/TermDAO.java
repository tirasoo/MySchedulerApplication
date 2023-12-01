package com.example.myschedulerapplication.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myschedulerapplication.Entities.Term;

import java.util.List;

/**
 *Term DAO Interface. Holds SQL statements for database.
 */
@Dao
public interface TermDAO{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("DELETE FROM terms_Table")
    void deleteAllTerms();

    @Query("SELECT * FROM terms_Table ORDER BY termID ASC")
    List<Term> getAllTerms();


}
