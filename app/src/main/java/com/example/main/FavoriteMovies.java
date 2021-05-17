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

public class FavoriteMovies extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_movies_activity);

        //set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_favorite__toolbar);
        setSupportActionBar(toolbar);

        //add back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get favorite movie container
        LinearLayout LL = findViewById(R.id.favorite_movies_activity_movies_list);

        //create new movie table class
        MovieTable MovieTable = new MovieTable(getApplicationContext());

        //get all favorite movies from the database
        ArrayList<Movie> movies = MovieTable.getFavoriteMovies();
        //append each favorite movie title into the favorite movie container
        for (int i = 0; i < movies.size(); i++) {
            MLItem item = new MLItem(getApplicationContext());
            item.Movie(movies.get(i));
            item.Favorite(movies.get(i).is_favorite);
            LL.addView(item);
        }

        //get remove from favorite button
        Button removeFavoriteButton = findViewById(R.id.favorite_movies_remove_favorite_btn);

        //click event handler for remove favorite button
        removeFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNoneFavoriteMovies();
            }
        });
    }

    /**
     * Get all movies that the user unchecked.
     *
     * @return
     */
    private ArrayList<Movie> getNonFavorites() {
        LinearLayout LL = findViewById(R.id.favorite_movies_activity_movies_list);
        ArrayList<Movie> noneFavorites = new ArrayList<>();
        for (int i = LL.getChildCount() - 1; i >= 0; i--) {
            MLItem movieItem = (MLItem) LL.getChildAt(i);
            if (!movieItem.Favorite()) {
                noneFavorites.add(movieItem.Movie());
            }
        }
        return noneFavorites;
    }

    /**
     * Update the database(remove all unchecked movies from favorite list)
     */
    private void setNoneFavoriteMovies() {
        ArrayList<Movie> noneFavorites = getNonFavorites();

        if (noneFavorites.size() < 1) { // no movies to update
            //show error message
            Snackbar snackBar = Snackbar.make(findViewById(R.id.favorite_movies_activity_base), "Unselect movies to remove from favorites", BaseTransientBottomBar.LENGTH_SHORT);
            snackBar.show();
        } else {
            //initiate new movie table class
            MovieTable MovieTable = new MovieTable(getApplicationContext());

            //update the database and get the status
            boolean is_updated = MovieTable.update(noneFavorites);

            String message = "";
            if (is_updated) { //updated
                clear(); //remove none favorite movies
                message = "Removed from favorites";
            } else {
                message = "Unknown error occurred!";
            }

            //show message to the user
            Snackbar snackBar = Snackbar.make(findViewById(R.id.favorite_movies_activity_base), message, BaseTransientBottomBar.LENGTH_SHORT);
            snackBar.show();
        }

    }

    /**
     * remove all unchecked movies from the favorite movies container
     */
    private void clear() {
        LinearLayout LL = findViewById(R.id.favorite_movies_activity_movies_list);
        for (int i = LL.getChildCount() - 1; i >= 0; i--) {
            MLItem movieItem = (MLItem) LL.getChildAt(i);
            if (!movieItem.Favorite()) {
                LL.removeViewAt(i);
            }
        }
    }
}

/*
REFERENCES

SQLite DATABASE TO HANDLE FAVORITE MOVIE
https://www.youtube.com/watch?v=JJqVPKrL2e8
*/