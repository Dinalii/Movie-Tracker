package com.example.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import database.MovieTable;
import models.Movie;

public class RatingsIndex extends AppCompatActivity {


    ArrayList<Movie> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ratings_index_activity);

        //set custom toolbar
        Toolbar ar_tbar = (Toolbar) findViewById(R.id.activity_ratings_toolbar);
        setSupportActionBar(ar_tbar);

        //set back button functionality
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get the find movie button and set it to disable
        Button findBtn = findViewById(R.id.activity_rating_index_find_btn);
        findBtn.setEnabled(false);

        //initiate mew movie table class
        MovieTable MovieTable = new MovieTable(getApplicationContext());

        //get all movies from the database
        movies = MovieTable.getAllMovies();

        //get movie titles container radio group
        RadioGroup moviesContainer = findViewById(R.id.activity_rating_index_movie_container);

        //add new radio button for each movie title
        for (int i = 0; i < movies.size(); i++) {
            RadioButton radio = new RadioButton(this);
            radio.setText(movies.get(i).title);
            radio.setId(i);
            moviesContainer.addView(radio);
        }

        //set the movie title radio button event listeners
        moviesContainer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //enable the find movie button
                findBtn.setEnabled(true);
            }
        });

        //set the find movie button click event listener
        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get selected movie
                Movie selected_movie = movies.get(moviesContainer.getCheckedRadioButtonId());

                //goto rating title activity with the move id
                Intent intent = new Intent(getApplicationContext(), RatingTitles.class);
                intent.putExtra("movie_id", selected_movie.id);
                startActivity(intent);
            }
        });

    }
}