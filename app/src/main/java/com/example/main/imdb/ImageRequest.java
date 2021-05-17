package com.example.main.imdb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageRequest extends AsyncTask<Void, Void, Bitmap> {

    public interface FinishedCallbacks {
        /**
         * This will execute if error occurred when the request send
         *
         * @param message
         */
        void onError(String message);

        /**
         * This will call before the request send
         */
        void onPreExecute();

        /**
         * This will call after a successful request
         *
         * @param image
         */
        void onPostExecute(Bitmap image);
    }

    FinishedCallbacks callbacks;
    private String url;

    public ImageRequest(FinishedCallbacks callbacks, String url) {
        this.url = url;
        this.callbacks = callbacks;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            //prepare the url
            URL urlConnection = new URL(url);
            //open the url connection
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();

            connection.setDoInput(true);
            connection.connect();

            //get the input stream
            InputStream input = connection.getInputStream();
            //convert the input stream into the bitmap
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            callbacks.onError(e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        callbacks.onPreExecute();
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if (result == null) return;
        super.onPostExecute(result);
        callbacks.onPostExecute(result);
    }

}


/*
REFERNCES

MOVIES APP - RETROFIT, GLIDE, RECYCLERVIEW AND MOVIEDB API PT 1
https://www.youtube.com/watch?v=qt3WCP-_uaY

MOVIES APP - RETROFIT, GLIDE, RECYCLERVIEW AND MOVIEDB API PT 2
https://www.youtube.com/watch?v=OOLFhtyCspA&t=154s

MOVIES APP - RETROFIT, GLIDE, RECYCLERVIEW AND MOVIEDB API PT 3
https://www.youtube.com/watch?v=4Gd2PIzU3zE&t=1320s

*/