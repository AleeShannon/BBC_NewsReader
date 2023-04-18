package com.example.bbc_newsreader;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * A singleton class to manage the favorite headlines.
 */
public class FavoriteHeadlines {
    private static final String FILENAME = "favorite_headlines";
    private static FavoriteHeadlines instance;
    private final ArrayList<Headline> favorites;
    private final Context context;

    /**
     * Private constructor for the singleton pattern.
     *
     * @param context The application context.
     */
    private FavoriteHeadlines(Context context) {
        this.context = context.getApplicationContext();
        favorites = loadFavorites();
    }
    /**
     * Gets the singleton instance of FavoriteHeadlines.
     *
     * @param context The application context.
     * @return The singleton instance of FavoriteHeadlines.
     */
    public static FavoriteHeadlines getInstance(Context context) {

        if (instance == null) {
            instance = new FavoriteHeadlines(context);
        }
        return instance;
    }
    /**
     * Adds a headline to the list of favorites.
     *
     * @param headline The headline to be added.
     */
    public void addFavorite(Headline headline) {
        favorites.add(headline);
        saveFavorites();
    }
    /**
     * Gets the list of favorite headlines.
     *
     * @return The list of favorite headlines.
     */
    public ArrayList<Headline> getFavorites() {
        return favorites;
    }
    /**
     * Loads the list of favorite headlines from the file.
     *
     * @return The list of favorite headlines.
     */
    private ArrayList<Headline> loadFavorites() {
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Headline> favorites = (ArrayList<Headline>) ois.readObject();
            ois.close();
            fis.close();
            return favorites;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    /**
     * Saves the list of favorite headlines to the file.
     */
    private void saveFavorites() {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(favorites);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

