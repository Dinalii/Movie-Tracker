package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import models.Movie;

public class MovieTable extends Database {

    public static final String T_NAME = "movie_database";

    public final String C_PRIMARY_KEY = "id";
    public final String C_TITLE = "title";
    public final String C_YEAR = "year";
    public final String C_DIRECTOR = "director";
    public final String C_RATING = "rating";
    public final String C_REVIEW = "review";
    public final String C_ACTORS = "actors";
    public final String C_IS_FAVORITE = "is_favorite";

    public MovieTable(Context context) {
        super(context);
    }

    /**
     * create new movie
     *
     * @param movie
     * @return
     */
    public boolean create(Movie movie) {
        ContentValues values = new ContentValues();
        values.put("title", movie.title);
        values.put("year", movie.year);
        values.put("director", movie.director);
        values.put("rating", movie.rating);
        values.put("review", movie.review);
        values.put("actors", movie.actors);
        values.put("is_favorite", movie.is_favorite);
        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insert(T_NAME, null, values) > 0;
        db.close();
        return createSuccessful;
    }

    /**
     * get all movies
     *
     * @return
     */
    public ArrayList<Movie> getAllMovies() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + T_NAME + " ORDER BY title";
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Movie> movies = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.id = cursor.getLong(cursor.getColumnIndex(C_PRIMARY_KEY));
                movie.title = cursor.getString(cursor.getColumnIndex(C_TITLE));
                movie.year = cursor.getInt(cursor.getColumnIndex(C_YEAR));
                movie.director = cursor.getString(cursor.getColumnIndex(C_DIRECTOR));
                movie.rating = cursor.getInt(cursor.getColumnIndex(C_RATING));
                movie.review = cursor.getString(cursor.getColumnIndex(C_REVIEW));
                movie.actors = cursor.getString(cursor.getColumnIndex(C_ACTORS));
                movie.is_favorite = cursor.getDouble(cursor.getColumnIndex(C_IS_FAVORITE)) == 1;
                movies.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return movies;
    }

    /**
     * search movie that matched with the given search terms
     * this will return a list that contains movies that is match eather title,
     * director or actress/actors with the given search terms
     *
     * @param search_term
     * @return
     */
    public ArrayList<Movie> searchMovies(String search_term) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + T_NAME +
                " WHERE " + C_TITLE + " LIKE ? " +
                " OR " + C_DIRECTOR + " LIKE ? " +
                " OR " + C_ACTORS + " LIKE ? " +
                " ORDER BY " + C_TITLE;

        Cursor cursor = db.rawQuery(query, new String[]{"%" + search_term + "%", "%" + search_term + "%", "%" + search_term + "%"});

        ArrayList<Movie> movies = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.id = cursor.getLong(cursor.getColumnIndex(C_PRIMARY_KEY));
                movie.title = cursor.getString(cursor.getColumnIndex(C_TITLE));
                movie.year = cursor.getInt(cursor.getColumnIndex(C_YEAR));
                movie.director = cursor.getString(cursor.getColumnIndex(C_DIRECTOR));
                movie.rating = cursor.getInt(cursor.getColumnIndex(C_RATING));
                movie.review = cursor.getString(cursor.getColumnIndex(C_REVIEW));
                movie.actors = cursor.getString(cursor.getColumnIndex(C_ACTORS));
                movie.is_favorite = cursor.getDouble(cursor.getColumnIndex(C_IS_FAVORITE)) == 1;
                movies.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return movies;
    }

    /**
     * get all favorite movies
     *
     * @return
     */
    public ArrayList<Movie> getFavoriteMovies() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + T_NAME + " WHERE " + C_IS_FAVORITE + " =1 ORDER BY title";
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Movie> movies = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.id = cursor.getLong(cursor.getColumnIndex(C_PRIMARY_KEY));
                movie.title = cursor.getString(cursor.getColumnIndex(C_TITLE));
                movie.year = cursor.getInt(cursor.getColumnIndex(C_YEAR));
                movie.director = cursor.getString(cursor.getColumnIndex(C_DIRECTOR));
                movie.rating = cursor.getInt(cursor.getColumnIndex(C_RATING));
                movie.review = cursor.getString(cursor.getColumnIndex(C_REVIEW));
                movie.actors = cursor.getString(cursor.getColumnIndex(C_ACTORS));
                movie.is_favorite = cursor.getDouble(cursor.getColumnIndex(C_IS_FAVORITE)) == 1;

                movies.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return movies;
    }

    /**
     * get specific movie
     *
     * @param id
     * @return
     */
    public Movie get(long id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + T_NAME + " WHERE " + C_PRIMARY_KEY + " =?";
        Cursor cursor = db.rawQuery(query, new String[]{Long.toString(id)});

        Movie movie = new Movie();
        if (cursor.moveToFirst()) {
            movie.id = cursor.getLong(cursor.getColumnIndex(C_PRIMARY_KEY));
            movie.title = cursor.getString(cursor.getColumnIndex(C_TITLE));
            movie.year = cursor.getInt(cursor.getColumnIndex(C_YEAR));
            movie.director = cursor.getString(cursor.getColumnIndex(C_DIRECTOR));
            movie.rating = cursor.getInt(cursor.getColumnIndex(C_RATING));
            movie.review = cursor.getString(cursor.getColumnIndex(C_REVIEW));
            movie.actors = cursor.getString(cursor.getColumnIndex(C_ACTORS));
            movie.is_favorite = cursor.getDouble(cursor.getColumnIndex(C_IS_FAVORITE)) == 1;

        } else {
            return null;
        }
        cursor.close();
        db.close();
        return movie;
    }

    /**
     * bulk update movies
     *
     * @param movies
     * @return
     */
    public boolean update(ArrayList<Movie> movies) {
        SQLiteDatabase db = this.getWritableDatabase();
        int affected = 0;
        for (int i = 0; i < movies.size(); i++) {
            ContentValues values = new ContentValues();
            values.put("title", movies.get(i).title);
            values.put("year", movies.get(i).year);
            values.put("director", movies.get(i).director);
            values.put("rating", movies.get(i).rating);
            values.put("review", movies.get(i).review);
            values.put("actors", movies.get(i).actors);
            values.put("is_favorite", movies.get(i).is_favorite);
            affected += db.update(T_NAME, values, "id =?", new String[]{Long.toString(movies.get(i).id)});
        }
        return affected > 0;
    }

    /**
     * update specific movie
     *
     * @param movie
     * @return
     */
    public boolean update(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", movie.title);
        values.put("year", movie.year);
        values.put("director", movie.director);
        values.put("rating", movie.rating);
        values.put("review", movie.review);
        values.put("actors", movie.actors);
        values.put("is_favorite", movie.is_favorite);
        int affected = db.update(T_NAME, values, "id =?", new String[]{Long.toString(movie.id)});

        return affected > 0;
    }
}
