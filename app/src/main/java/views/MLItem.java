package views;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.main.R;

import models.Movie;

public class MLItem extends LinearLayout {
    TextView titleText;
    CheckBox isFavoriteCheckBox;
    Movie movie;

    public MLItem(Context context) {
        super(context);
        inflate(context, R.layout.movie_list_item, this);

        titleText = findViewById(R.id.movie_item_title_text);
        isFavoriteCheckBox = findViewById(R.id.movie_item_favorite_chk);

        isFavoriteCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                movie.is_favorite = isChecked;
            }
        });
    }

    /**
     * Get the title
     *
     * @param title
     */
    public void Title(String title) {
        titleText.setText(title);
    }

    /**
     * Set the title
     *
     * @return
     */
    public String Title() {
        return titleText.getText().toString();
    }

    /**
     * Set this as status
     *
     * @param is_favorite
     */
    public void Favorite(boolean is_favorite) {
        this.movie.is_favorite = is_favorite;
        isFavoriteCheckBox.setChecked(is_favorite);
    }

    /**
     * get the favorite status
     *
     * @return
     */
    public boolean Favorite() {
        return this.movie.is_favorite;
    }

    /**
     * set the movie
     *
     * @param movie
     */
    public void Movie(Movie movie) {
        titleText.setText(movie.title);
        this.movie = movie;
    }

    /**
     * get the movie
     *
     * @return
     */
    public Movie Movie() {
        return movie;
    }
}


/*
REFERENCES

SQLite + Android - Display Data in RecyclerView (Book Library App) | Part 3
https://www.youtube.com/watch?v=VQKq9RHMS_0&list=PLSrm9z4zp4mGK0g_0_jxYGgg3os9tqRUQ&index=3

*/