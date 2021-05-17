package com.example.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import database.MovieTable;
import models.Movie;
import views.MLItem;

public class DisplayMovies extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_movies_activity);

        //Set the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.display_activity_toolbar);
        setSupportActionBar(toolbar);

        //Enable back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayout LL = findViewById(R.id.display_movies_activity_movie_list);

        //Fetch and add all the movies to main container.
        MovieTable MovieTable = new MovieTable(getApplicationContext());
        ArrayList<Movie> movies = MovieTable.getAllMovies();
        for (int i = 0; i < movies.size(); i++) {
            MLItem item = new MLItem(getApplicationContext());
            Movie movie = movies.get(i);
            movie.is_favorite = false;
            item.Movie(movie);

            LL.addView(item);
        }

        //declare add to favorite list button events.
        Button addFavoriteButton = findViewById(R.id.display_movies_add_favorite_btn);
        addFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set all selected movies as favorite movies.
                setFavoriteMovies();
            }
        });
    }

    /**
     * Get all the movies that was assigned by user as favorite.
     *
     * @return
     */
    private ArrayList<Movie> getFavorites() {
        LinearLayout LL = findViewById(R.id.display_movies_activity_movie_list);
        ArrayList<Movie> favorites = new ArrayList<>();
        for (int i = 0; i < LL.getChildCount(); i++) {
            MLItem movieItem = (MLItem) LL.getChildAt(i);
            if (movieItem.Favorite()) {
                favorites.add(movieItem.Movie());
            }
        }
        return favorites;
    }

    /**
     * Update all the selected movies
     */
    private void setFavoriteMovies() {
        ArrayList<Movie> favorites = getFavorites();
        if (favorites.size() < 1) {
            //no movies selected
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.display_movies_base), "Select movies to add favorites list", BaseTransientBottomBar.LENGTH_SHORT);
            mySnackbar.show();
        } else {
            //update database
            MovieTable MovieTable = new MovieTable(getApplicationContext());
            boolean is_updated = MovieTable.update(getFavorites());
            clear();

            String message = "";
            if (is_updated) {
                message = "Added";
            } else {
                message = "Unknown Error Occurred!";
            }
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.display_movies_base), message, BaseTransientBottomBar.LENGTH_SHORT);
            mySnackbar.show();
        }

    }

    /**
     * Remove all favorite movies from the LinearLayout.
     */
    private void clear() {
        LinearLayout LL = findViewById(R.id.display_movies_activity_movie_list);
        for (int i = 0; i < LL.getChildCount(); i++) {
            MLItem movieItem = (MLItem) LL.getChildAt(i);
            if (movieItem.Favorite()) {
                movieItem.Favorite(false);
            }
        }
    }
}

/*
REFERENCES

Android RecyclerView – Sort Ascending/Descending
https://camposha.info/android-recyclerview-sort-ascending-descending/

How to save CheckBox values in SQlite Database and Load it later?
https://www.coderzheaven.com/2012/03/13/how-to-save-checkbox-values-in-sqlite-database-and-load-it-later/


*/