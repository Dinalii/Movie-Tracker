package com.example.main.imdb;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.IDN;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;


public class ImdbMovieRequest extends AsyncTask<String, Void, ImdbMovieRequest.postExecuteArgs> {

    public class postExecuteArgs {
        ArrayList<Result> movies = null;
        boolean has_errors = false;
        String error_message = null;
    }

    public class Result {
        public String id;
        public String iurl;
        public String title;
        public double rating;
    }

    public interface FinishedCallbacks {
        /**
         * this will call before send the request
         */
        void onPreExecute();

        /**
         * this will call after a successful request
         *
         * @param data
         */
        void onPostExecute(ArrayList<Result> data);

        /**
         * this will call if an error occurred when the request sending
         *
         * @param message
         */
        void onError(String message);
    }

    private FinishedCallbacks callbacks;
    private Context context;

    public ImdbMovieRequest(Context context, FinishedCallbacks callbacks) {
        this.callbacks = callbacks;
        this.context = context;
    }

    @Override
    protected postExecuteArgs doInBackground(String... title) {
        ArrayList<Result> results = new ArrayList<>();

        try {
            //build the endpoint url
            URL url = new URL("https://imdb-api.com/en/API/SearchTitle/k_2cxlyp4c/" + title[0]);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), IDN.toASCII(url.getHost()), url.getPort(),
                    url.getPath(), url.getQuery(), url.getRef());
            url = new URL(uri.toASCIIString());

            //open the connection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStreamReader IsReader = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader bReader = new BufferedReader(IsReader);
            StringBuilder sBuilder = new StringBuilder();

            String line;
            while ((line = bReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }
            bReader.close();

            String jsonString = sBuilder.toString();
            JSONObject jsonObject = new JSONObject(jsonString);

            //check whether the json object has error message string
            boolean has_errors = jsonObject.has("errorMessage")
                    && !jsonObject.isNull("errorMessage")
                    && !jsonObject.getString("errorMessage").trim().isEmpty();
            if (has_errors) {
                postExecuteArgs parms = new postExecuteArgs();
                parms.has_errors = true;
                parms.error_message = jsonObject.getString("errorMessage");
                return parms;
            }

            if (jsonObject.has("results") && !jsonObject.isNull("results")) {
                //get the results array as json array
                JSONArray JsonResults = jsonObject.getJSONArray("results");
                //add each movie into the results list
                for (int i = 0; i < JsonResults.length(); i++) {
                    Result result = new Result();
                    result.id = JsonResults.getJSONObject(i).getString("id");
                    result.iurl = JsonResults.getJSONObject(i).getString("image");
                    ;
                    result.title = JsonResults.getJSONObject(i).getString("title");
                    ;
                    results.add(result);
                }
            }
            postExecuteArgs parms = new postExecuteArgs();
            parms.movies = results;
            return parms;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            postExecuteArgs parms = new postExecuteArgs();
            parms.error_message = e.getMessage();
            parms.has_errors = true;
            return parms;
        } catch (IOException e) {
            e.printStackTrace();
            postExecuteArgs parms = new postExecuteArgs();
            parms.error_message = e.getMessage();
            parms.has_errors = true;
            return parms;
        } catch (JSONException e) {
            e.printStackTrace();
            postExecuteArgs parms = new postExecuteArgs();
            parms.error_message = e.getMessage();
            parms.has_errors = true;
            return parms;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            postExecuteArgs parms = new postExecuteArgs();
            parms.error_message = e.getMessage();
            parms.has_errors = true;
            return parms;
        }
    }

    /**
     * Execute this after successful Api request
     *
     * @param parms
     */
    @Override
    protected void onPostExecute(postExecuteArgs parms) {
        if (parms.has_errors) {
            callbacks.onError(parms.error_message);
            return;
        }
        ;
        if (parms.movies == null) return;
        callbacks.onPostExecute(parms.movies);
    }

    /**
     * Execute this before send the request
     */
    @Override
    protected void onPreExecute() {
        callbacks.onPreExecute();
    }
}



/*
REFERNCES

To get the API key from IMDB
https://imdb-api.com/

MOVIES APP - RETROFIT, GLIDE, RECYCLERVIEW AND MOVIEDB API PT 1
https://www.youtube.com/watch?v=qt3WCP-_uaY

MOVIES APP - RETROFIT, GLIDE, RECYCLERVIEW AND MOVIEDB API PT 2
https://www.youtube.com/watch?v=OOLFhtyCspA&t=154s

MOVIES APP - RETROFIT, GLIDE, RECYCLERVIEW AND MOVIEDB API PT 3
https://www.youtube.com/watch?v=4Gd2PIzU3zE&t=1320s

 */