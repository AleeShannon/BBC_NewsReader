package com.example.bbc_newsreader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class FavoritesDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favorites.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "favorites";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_LINK = "link";
    private static final String COLUMN_DATE = "date";

    public FavoritesDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_LINK + " TEXT,"
                + COLUMN_DATE + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addFavorite(Headline headline) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, headline.getTitle());
        contentValues.put(COLUMN_DATE, headline.getDate());
        contentValues.put(COLUMN_DESCRIPTION, headline.getDescription());
        contentValues.put(COLUMN_LINK, headline.getLink());

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public ArrayList<Headline> getAllFavorites() {
        ArrayList<Headline> headlines = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Headline headline = new Headline();
                headline.setTitle(cursor.getString(1));
                headline.setDescription(cursor.getString(2));
                headline.setLink(cursor.getString(3));
                headline.setDate(cursor.getString(4));
                headlines.add(headline);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return headlines;
    }

    public void deleteFavorite(String link) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_LINK + "=?", new String[]{link});
        db.close();
    }

    public boolean isFavorite(String link) {
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_LINK + "=?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[] {link});
        boolean isFavorite = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isFavorite;
    }
}

