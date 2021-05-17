package com.example.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;

import com.example.main.validations.IFilter;

import database.MovieTable;
import models.Movie;

public class EditMovie extends AppCompatActivity {

    /**
     * Currently opened movie id
     */
    protected long movie_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_movie_activity);

        //set custom app bar as default app bar
        Toolbar ar_tbar = (Toolbar) findViewById(R.id.activity_edit_movie_toolbar);
        setSupportActionBar(ar_tbar);
        //add back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText YearInput = (EditText) findViewById(R.id.edit_movie_year_txt);
        //set the input range filter.
        //this will allow the user to enter an year grater then 1859.
        YearInput.setFilters(new InputFilter[]{new IFilter(1895, IFilter.Boundary.min)});

        //update button event handlers
        Button updateButton = findViewById(R.id.register_movie_save_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check that the given data is correct.
                if (!Validate()) return;

                //update the database.
                boolean is_updated = updateData();

                //if successfully updated, goto EditMovieIndex activity
                if (is_updated) {
                    Intent intent = new Intent(v.getContext(), EditMovieIndex.class);
                    startActivity(intent);
                } else {
                    // if fails to update, show an error message
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.activity_edit_base), "Faild to update. Unknown error occurred.", BaseTransientBottomBar.LENGTH_SHORT);
                    mySnackbar.show();
                }

            }
        });


        Intent intent = getIntent();

        //get the current movie.
        movie_id = intent.getLongExtra("movie_id", -1);
        Movie movie = (new MovieTable(getApplicationContext())).get(movie_id);

        //fill the input components according to the current movie.
        fillData(movie);
    }

    /**
     * Get filled data.
     *
     * @return
     */
    private Movie getData() {
        EditText titleText = findViewById(R.id.edit_movie_title_txt);
        EditText yearText = findViewById(R.id.edit_movie_year_txt);
        EditText directorText = findViewById(R.id.edit_movie_director_txt);
        EditText actorText = findViewById(R.id.edit_movie_actors_txt);
        RatingBar ratingText = findViewById(R.id.edit_movie_rating_bar);
        EditText reviewText = findViewById(R.id.edit_movie_review_txt);
        SwitchMaterial favoriteSwitch = findViewById(R.id.edit_movie_favorite_switch);

        Movie movie = new Movie();
        movie.id = movie_id;
        movie.title = titleText.getText().toString();
        movie.year = Integer.parseInt(yearText.getText().toString());
        movie.director = directorText.getText().toString();
        movie.actors = actorText.getText().toString();
        movie.rating = (int) ratingText.getRating();
        movie.review = reviewText.getText().toString();
        movie.is_favorite = favoriteSwitch.isChecked();

        return movie;
    }

    /**
     * Validate filled data
     *
     * @return
     */
    private boolean Validate() {
        EditText titleText = findViewById(R.id.edit_movie_title_txt);
        EditText yearText = findViewById(R.id.edit_movie_year_txt);


        boolean is_filled_required_fields = false;
        is_filled_required_fields = titleText.getText().toString().length() > 0
                && yearText.getText().toString().length() > 0;

        if (!is_filled_required_fields) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.activity_edit_base), "Please fill required fields", BaseTransientBottomBar.LENGTH_SHORT);
            mySnackbar.show();
        }
        return is_filled_required_fields;
    }

    /**
     * Fill the inputs according to the given movie
     *
     * @param movie
     */
    private void fillData(Movie movie) {
        EditText titleText = findViewById(R.id.edit_movie_title_txt);
        EditText yearText = findViewById(R.id.edit_movie_year_txt);
        EditText directorText = findViewById(R.id.edit_movie_director_txt);
        EditText actorText = findViewById(R.id.edit_movie_actors_txt);
        RatingBar ratingText = findViewById(R.id.edit_movie_rating_bar);
        EditText reviewText = findViewById(R.id.edit_movie_review_txt);
        SwitchMaterial favoriteSwitch = findViewById(R.id.edit_movie_favorite_switch);

        titleText.setText(movie.title);
        yearText.setText(Integer.toString(movie.year));
        directorText.setText(movie.director);
        actorText.setText(movie.actors);
        ratingText.setRating(movie.rating);
        reviewText.setText(movie.review);
        favoriteSwitch.setChecked(movie.is_favorite);
    }

    /**
     * Update the database according to the given inputs
     *
     * @return
     */
    private boolean updateData() {
        return (new MovieTable(getApplicationContext())).update(getData());
    }
}


/*

REFERENCES

SQLite + Android - Update Table Data (Book Library App) | Part 4
https://www.youtube.com/watch?v=wK-JccC-i4Y&t=342s

*/
