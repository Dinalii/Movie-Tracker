package com.example.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //get all buttons
        Button registerButton = findViewById(R.id.register_movie_btn);
        Button displayMoviesButton = findViewById(R.id.display_movies_btn);
        Button favoriteMoviesButton = findViewById(R.id.favorite_movies_btn);
        Button editMovieButton = findViewById(R.id.edit_movie_button);
        Button searchButton = findViewById(R.id.search_button);
        Button ratingButton = findViewById(R.id.rating_button);

        //add click event handlers
        // Opening of "Movie Register Activity " activity using button click
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RegisterMovie.class);
                startActivity(intent);
            }
        });

        // Opening of "Dispaly Movie Activity " activity using button click
        displayMoviesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplayMovies.class);
                startActivity(intent);
            }
        });

        // Opening of "Favourites Movies Activity " activity using button click
        favoriteMoviesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FavoriteMovies.class);
                startActivity(intent);
            }
        });

        // Opening of "Edit Movies Activity " activity using button click
        editMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditMovieIndex.class);
                startActivity(intent);
            }
        });

        // Opening of "Search Movies Activity " activity using button click
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Search.class);
                startActivity(intent);
            }
        });

        // Opening of "Movie Ratings Activity " activity using button click
        ratingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RatingsIndex.class);
                startActivity(intent);
            }
        });
    }
}