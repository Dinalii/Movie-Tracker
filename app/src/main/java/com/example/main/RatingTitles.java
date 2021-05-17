package com.example.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import database.MovieTable;

import com.example.main.imdb.ImdbMovieRequest;

import models.Movie;

public class RatingTitles extends AppCompatActivity {
    //previously selected selected movie
    Movie selected_movie;

    //matched movie titles in Imdb
    ArrayList<String> titles;

    //matched movies in Imdb
    ArrayList<ImdbMovieRequest.Result> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_titles_activity);

        //set custom toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_rating_title_toolbar);
        setSupportActionBar(toolbar);

        //set back button functionality
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        //get selected movie id(previously selected)
        long movie_id = intent.getLongExtra("movie_id", -1);

        //get movie title list view
        ListView titles = findViewById(R.id.rating_titles_list_view);

        //click event listener for movie title list view
        titles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //goto the movie image activity
                Intent intent = new Intent(getBaseContext(), RatingImage.class);
                intent.putExtra("image_url", results.get(position).iurl);
                intent.putExtra("movie_id", movie_id);
                startActivity(intent);
            }
        });

        selected_movie = (new MovieTable(getApplicationContext())).get(movie_id);

        //search movies
        searchMovies(selected_movie.title);
    }

    /**
     * Search all the movies that matched with given title from imdb database
     *
     * @param title
     */
    private void searchMovies(String title) {
        Context context = this;
        if (title == null) return; // stop if the title is null

        //callbacks for API request
        ImdbMovieRequest.FinishedCallbacks callbacks = new ImdbMovieRequest.FinishedCallbacks() {


            /**
             * This will run before the request is done.
             */
            @Override
            public void onPreExecute() {
                //set title list view as invisible and show the loader text view to visible
                ListView titlesList = findViewById(R.id.rating_titles_list_view);
                titlesList.setVisibility(View.INVISIBLE);

                TextView loaderText = findViewById(R.id.activity_rating_titles_loading_text);
                loaderText.setVisibility(View.VISIBLE);
            }

            /**
             * This will run after successful Api request
             * @param data
             */
            @Override
            public void onPostExecute(ArrayList<ImdbMovieRequest.Result> data) {
                if (data.size() < 1) { //check whether there are no matched movies
                    //show error message and stop executing
                    String message = "No Matching Movies Found!";
                    TextView loader = findViewById(R.id.activity_rating_titles_loading_text);
                    loader.setText(message);
                    return;
                }

                //get titles list view
                ListView titlesList = findViewById(R.id.rating_titles_list_view);

                //store all the matched movies
                results = data;

                //matched movie titles
                titles = new ArrayList<>();

                //add each matched movie title into the titles list
                for (int i = 0; i < data.size(); i++) {
                    titles.add(data.get(i).title);
                }

                //update the titles list view
                ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, android.R.id.text1, titles);
                titlesList.setAdapter(modeAdapter);

                //show the loader text view
                titlesList.setVisibility(View.VISIBLE);

                //hide the loader
                TextView loaderText = findViewById(R.id.activity_rating_titles_loading_text);
                loaderText.setVisibility(View.GONE);
            }

            /**
             * Error occured during the execution
             * @param message
             */
            @Override
            public void onError(String message) {
                //show error message
                message = (message != null) ? message : "An error occurred!";
                TextView loaderText = findViewById(R.id.activity_rating_titles_loading_text);
                loaderText.setText(message);
            }
        };

        //execute the Api request
        new ImdbMovieRequest(context, callbacks).execute(title);
    }
}