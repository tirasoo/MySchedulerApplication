package com.example.myschedulerapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myschedulerapplication.Database.Repository;
import com.example.myschedulerapplication.Entities.Term;
import com.example.myschedulerapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * TermLIst class--->Main Term screen,and Contains list of all Terms.
 */
public class TermsList extends AppCompatActivity {
    private DateTimeFormatter formatter;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        FloatingActionButton floatingActionButton2 = findViewById(R.id.floatingActionButton2);
        floatingActionButton2.setOnClickListener(view -> {
            Intent intent = new Intent(TermsList.this, DetailedTermView.class);
            startActivity(intent);
        });
        repository = new Repository(getApplication());
        List<Term> allTerms = repository.getAllTerms();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final TermAdapter termAdapter = new TermAdapter(this );
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_terms_details, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        if (item.getItemId() == R.id.termSave) {
            Repository repo = new Repository(getApplication());
            Term term = new Term(1, "Term 1", "10/31/2023", "11/11/2023");
            repo.insert(term);
            term = new Term(2, "Term 2", "10/31/2023", "12/31/2023");
            repo.insert(term);
            List<Term> allTerms = repository.getAllTerms();
            RecyclerView recyclerView = findViewById(R.id.recyclerview);
            final TermAdapter termAdapter = new TermAdapter(this);
            recyclerView.setAdapter(termAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            termAdapter.setTerms(allTerms);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * the method refreshes the current page-the termslist- after saving a new term to it.To reflect
     * the new term on the termlist activity.
     */
    @Override
    protected void onResume() {
        super.onResume();
        List<Term> allTerms=repository.getAllTerms();
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        final TermAdapter termAdapter=new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);
    }
}