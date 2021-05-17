package com.example.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.main.imdb.ImageRequest;

public class RatingImage extends AppCompatActivity {

    long movie_id;
    String image_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_image_activity);

        //add toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_rating_image_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //extract the extra parameters sended by parent activity.
        Intent intent = getIntent();
        image_url = intent.getStringExtra("image_url");
        movie_id = intent.getLongExtra("movie_id", -100);

        TextView loader = findViewById(R.id.activity_rating_image_loading_text);
        ImageView imageView = findViewById(R.id.activity_rating_image_view);

        /**
         * Override AsyncTask interface's methods and add new methods.
         */
        ImageRequest.FinishedCallbacks callbacks = new ImageRequest.FinishedCallbacks() {
            /**
             * Run before the request send.
             * This will hides the elements exept the loader textView.
             */
            @Override
            public void onPreExecute() {
                loader.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.INVISIBLE);
            }

            /**
             * Run this after the request successfully finished.
             * This will render the requested image and also show
             * the elements and hides the loader textView.
             * @param image
             */
            @Override
            public void onPostExecute(Bitmap image) {
                loader.setVisibility(View.INVISIBLE);
                imageView.setImageBitmap(image);
                imageView.setVisibility(View.VISIBLE);
            }

            /**
             * Run this if the request has errors or Exceptions.
             * This will show the error message
             * @param message
             */
            @Override
            public void onError(String message) {
                loader.setText(message != null ? message : "An error occurred!");
            }
        };

        /**
         * Request the movie image and render it asynchronously.
         */
        new ImageRequest(callbacks, image_url).execute();

    }

    /**
     * Set Toolbar actions
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /**
             * Back button event handler
             */
            case android.R.id.home:
                Intent intent = new Intent(getBaseContext(), RatingTitles.class);
                intent.putExtra("movie_id", movie_id);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}