package com.example.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import database.MovieTable;
import models.Movie;

public class EditMovieIndex extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_movie_index_activity);

        //set custom toolbar
        Toolbar ar_tbar = (Toolbar) findViewById(R.id.activity_edit_movie_index_toolbar);
        setSupportActionBar(ar_tbar);

        //add back btn functionality
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //initiate a MovieTable class instance
        MovieTable MovieTable = new MovieTable(getApplicationContext());

        //get all movies from the database
        ArrayList<Movie> movies = MovieTable.getAllMovies();

        //movie titles
        ArrayList<String> movie_titles = new ArrayList<>();

        //add each of movie titles to the movie_title list
        for (int i = 0; i < movies.size(); i++) {
            movie_titles.add(movies.get(i).title);
        }

        //add movie_title elements to the movie title container list
        ListView moviesContainer = findViewById(R.id.activity_edit_index_movie_list_view);
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, movie_titles);
        moviesContainer.setAdapter(modeAdapter);

        //item click handler for the movie title container
        moviesContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get selected movie and goto the Edit movie activity with the movie id
                Movie selected_movie = movies.get(position);
                Intent intent = new Intent(getApplicationContext(), EditMovie.class);
                intent.putExtra("movie_id", selected_movie.id);
                startActivity(intent);
            }
        });
    }
}

/*

REFERENCES

SQLite + Android - Update Table Data (Book Library App) | Part 4
https://www.youtube.com/watch?v=wK-JccC-i4Y&t=342s

*/


