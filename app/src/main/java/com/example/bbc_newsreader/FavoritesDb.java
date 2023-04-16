package com.example.bbc_newsreader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class FavoritesDb extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Favorites.db";

    public FavoritesDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE favorites ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "title TEXT,"
                + "date TEXT,"
                + "description TEXT,"
                + "link TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS favorites");
        onCreate(db);
    }

    public void addFavorite(Favorite favorite) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", favorite.getTitle());
        values.put("date", favorite.getDate());
        values.put("description", favorite.getDescription());
        values.put("link", favorite.getLink());

        db.insert("favorites", null, values);
    }

    public void removeFavorite(Favorite favorite) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete("favorites", "title = ? AND date = ? AND description = ? AND link = ?",
                new String[] { favorite.getTitle(), favorite.getDate(),
                        favorite.getDescription(), favorite.getLink() });
    }

    public List<Favorite> getFavorites() {
        List<Favorite> favorites = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM favorites", null);

        if (cursor.moveToFirst()) {
            int titleIndex = cursor.getColumnIndex("title");
            int dateIndex = cursor.getColumnIndex("date");
            int descriptionIndex = cursor.getColumnIndex("description");
            int linkIndex = cursor.getColumnIndex("link");
            do {
                String title = titleIndex != -1 ? cursor.getString(titleIndex) : "";
                String date = dateIndex != -1 ? cursor.getString(dateIndex) : "";
                String description = descriptionIndex != -1 ? cursor.getString(descriptionIndex) : "";
                String link = linkIndex != -1 ? cursor.getString(linkIndex) : "";

                Favorite favorite = new Favorite(title, date, description, link);
                favorites.add(favorite);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return favorites;
    }
}

