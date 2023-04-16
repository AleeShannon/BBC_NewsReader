package com.example.bbc_newsreader;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FavoriteHeadlines {
    private static final String FILENAME = "favorite_headlines";
    private static FavoriteHeadlines instance;
    private ArrayList<Headline> favorites;
    private Context context;

    private FavoriteHeadlines(Context context) {
        this.context = context.getApplicationContext();
        favorites = loadFavorites();
    }

    public static FavoriteHeadlines getInstance(Context context) {

        if (instance == null) {
            instance = new FavoriteHeadlines(context);
        }
        return instance;
    }

    public void addFavorite(Headline headline) {
        favorites.add(headline);
        saveFavorites();
    }

    public ArrayList<Headline> getFavorites() {
        return favorites;
    }

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

