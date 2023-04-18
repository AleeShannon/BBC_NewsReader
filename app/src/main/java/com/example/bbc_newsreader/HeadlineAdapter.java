package com.example.bbc_newsreader;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;

import java.util.ArrayList;

public class HeadlineAdapter extends ArrayAdapter<Headline> {

    private Context context;
    private ArrayList<Headline> headlines;
    private FavoritesDB favoritesDb;

    public HeadlineAdapter(Context context, ArrayList<Headline> headlines, FavoritesDB favoritesDb) {
        super(context, R.layout.list_item, headlines);
        this.context = context;
        this.headlines = headlines;
        this.favoritesDb = favoritesDb;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);

        TextView titleTextView = rowView.findViewById(R.id.title_TextView);
        TextView dateTextView = rowView.findViewById(R.id.date_TextView);
        TextView descriptionTextView = rowView.findViewById(R.id.description_textView);
        Button openArticleButton = rowView.findViewById(R.id.open_article_button);
        Button addFavoritesButton = rowView.findViewById(R.id.add_favorites_button);

        Headline headline = headlines.get(position);

        titleTextView.setText(headline.getTitle());
        dateTextView.setText(headline.getDate());
        descriptionTextView.setText(headline.getDescription());

        openArticleButton.setOnClickListener(v -> {
            Log.d("HeadlineAdapter", "Opening article: " + headline.getLink());
            Uri uri = Uri.parse(headline.getLink());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            Log.d("HeadlineAdapter", "Open article button clicked");
        });


        addFavoritesButton.setOnClickListener(v -> {
            if (favoritesDb.addFavorite(headline)) {
                addFavoritesButton.setText("Added to favorites");
                Log.d("Favorites", "Successfully added to favorites");
            } else {
                addFavoritesButton.setText("Error");
                Log.d("Favorites", "Failed to add to favorites");
            }
        });

        addFavoritesButton.setFocusable(false);

        return rowView;
    }
}
