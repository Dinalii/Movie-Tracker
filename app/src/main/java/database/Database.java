package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    //database version
    private static final int DATABASE_VERSION = 1;
    //database name
    protected static final String DATABASE_NAME = "movie_databases.db";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + MovieTable.T_NAME +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "year INT, " +
                "director TEXT, " +
                "actors TEXT, " +
                "rating INT, " +
                "is_favorite BOOLEAN, " +
                "review TEXT ) ";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + MovieTable.T_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}
