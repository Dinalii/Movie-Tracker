package com.example.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import database.MovieTable;
import models.Movie;

public class Search extends AppCompatActivity {
    //this will store all of the matching movies
    ArrayList<Movie> movies = new ArrayList<>();
    //this will store all of the matching movie titles
    ArrayList<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        //set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_search_movie_index_toolbar);
        setSupportActionBar(toolbar);

        //set back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get the search button
        Button searchButton = findViewById(R.id.search_movie_lookup_btn);

        //click handler for the search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //search the matching movies
                search();
            }
        });
    }

    /**
     * Search the movies
     */
    private void search() {
        EditText searchTermsText = findViewById(R.id.search_movie_search_term_text);
        MovieTable MovieTable = new MovieTable(getApplicationContext());
        movies = MovieTable.searchMovies(searchTermsText.getText().toString());
        titles.clear();
        for (int i = 0; i < movies.size(); i++) {
            titles.add(movies.get(i).title);
        }
        ListView moviesContainer = findViewById(R.id.search_movie_movies_container);
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, titles);
        moviesContainer.setAdapter(modeAdapter);
    }

}

/*
REFERENCES

SEARCH FILTER IN ANDROID SQLITE DATABASE AND RECYCLERVIEW
https://www.youtube.com/watch?v=PPXiZDWPOcI

Movie Search Android Application in Android Studio
https://www.youtube.com/watch?v=dQb8OkrXUkI


*/