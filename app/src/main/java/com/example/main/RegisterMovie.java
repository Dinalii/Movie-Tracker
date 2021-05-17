package com.example.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import com.example.main.validations.IFilter;

import database.MovieTable;
import models.Movie;

public class RegisterMovie extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_movie_activity);

        //set custom toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_register_toolbar);
        setSupportActionBar(toolbar);

        //set the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //add filter for year input
        EditText YearInput = (EditText) findViewById(R.id.register_movie_year_txt);
        YearInput.setFilters(new InputFilter[]{new IFilter(1895, IFilter.Boundary.min)});

        //add filter for rating input
        EditText RatingInput = (EditText) findViewById(R.id.register_movie_rating_txt);
        RatingInput.setFilters(new InputFilter[]{new IFilter(1, 10)});

        //get the save movie button
        Button saveButton = findViewById(R.id.register_movie_save_button);

        //add click event listener for save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check whether the inputs are filled and valid
                if (!Validate()) return;

                //create new table movie class
                MovieTable i = new MovieTable(getApplicationContext());

                //create new movie with given data and get the states
                boolean is_created = i.create(getData());

                String message = "";
                if (is_created) { //successfully created
                    clearData(); //clear the filled inputs
                    message = "Saved";
                } else {
                    message = "Unknown Error Occurred!";
                }
                //show status message
                Snackbar snackBar = Snackbar.make(findViewById(R.id.activity_register_base_layout), message, BaseTransientBottomBar.LENGTH_SHORT);
                snackBar.show();
            }
        });
    }

    /**
     * get filled data
     *
     * @return
     */
    private Movie getData() {
        EditText titleText = findViewById(R.id.register_movie_title_txt);
        EditText yearText = findViewById(R.id.register_movie_year_txt);
        EditText directorText = findViewById(R.id.register_movie_director_txt);
        EditText actorText = findViewById(R.id.register_movie_actors_txt);
        EditText ratingText = findViewById(R.id.register_movie_rating_txt);
        EditText reviewText = findViewById(R.id.register_movie_review_txt);

        Movie movie = new Movie();
        movie.title = titleText.getText().toString();
        movie.year = Integer.parseInt(yearText.getText().toString());
        movie.director = directorText.getText().toString();
        movie.actors = actorText.getText().toString();
        movie.rating = Integer.parseInt(ratingText.getText().toString());
        movie.review = reviewText.getText().toString();

        return movie;
    }

    /**
     * clear the inputs
     */
    private void clearData() {
        EditText titleText = findViewById(R.id.register_movie_title_txt);
        EditText yearText = findViewById(R.id.register_movie_year_txt);
        EditText directorText = findViewById(R.id.register_movie_director_txt);
        EditText actorText = findViewById(R.id.register_movie_actors_txt);
        EditText ratingText = findViewById(R.id.register_movie_rating_txt);
        EditText reviewText = findViewById(R.id.register_movie_review_txt);

        titleText.setText("");
        yearText.setText("");
        directorText.setText("");
        actorText.setText("");
        ratingText.setText("");
        reviewText.setText("");
    }

    /**
     * Validate the filled data
     * This will show an error message if the filled data not valid.
     *
     * @return true or false according validate or not
     */
    private boolean Validate() {
        EditText titleText = findViewById(R.id.register_movie_title_txt);
        EditText yearText = findViewById(R.id.register_movie_year_txt);
        EditText ratingText = findViewById(R.id.register_movie_rating_txt);


        boolean is_filled_required_fields = false;
        is_filled_required_fields = titleText.getText().toString().length() > 0
                && yearText.getText().toString().length() > 0
                && ratingText.getText().toString().length() > 0;

        if (!is_filled_required_fields) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.activity_register_base_layout), "Please fill required fields", BaseTransientBottomBar.LENGTH_SHORT);
            mySnackbar.show();
        }
        return is_filled_required_fields;
    }
}