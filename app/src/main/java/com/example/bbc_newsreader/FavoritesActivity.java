package com.example.bbc_newsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    private ListView listView;
    private FavoritesDB favoritesDb;
    private ArrayList<Headline> headlines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites);

        listView = findViewById(R.id.favorites_list_view);
        favoritesDb = new FavoritesDB(this);
        headlines = favoritesDb.getAllFavorites();
        listView.setAdapter(new FavoritesAdapter(this, headlines, favoritesDb));
    }
}
