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
/**
 * Custom ArrayAdapter for displaying headlines.
 */
public class HeadlineAdapter extends ArrayAdapter<Headline> {
    /**
     * The context used to inflate the layout.
     */
    private Context context;
    /**
     * The list of headlines to be displayed.
     */
    private ArrayList<Headline> headlines;
    /**
     * The database used for managing favorites.
     */
    private FavoritesDB favoritesDb;

    /**
     * Constructor for the HeadlineAdapter.
     *
     * @param context    The context used to inflate the layout.
     * @param headlines  The list of headlines to be displayed.
     * @param favoritesDb The database used for managing favorites.
     */
    public HeadlineAdapter(Context context, ArrayList<Headline> headlines, FavoritesDB favoritesDb) {
        super(context, R.layout.list_item, headlines);
        this.context = context;
        this.headlines = headlines;
        this.favoritesDb = favoritesDb;
    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view we want.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
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

        // Hide the date and description initially
        dateTextView.setVisibility(View.GONE);
        descriptionTextView.setVisibility(View.GONE);

        titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the visibility of the date and description views
                if (dateTextView.getVisibility() == View.VISIBLE) {
                    dateTextView.setVisibility(View.GONE);
                    descriptionTextView.setVisibility(View.GONE);
                } else {
                    dateTextView.setVisibility(View.VISIBLE);
                    descriptionTextView.setVisibility(View.VISIBLE);
                }
            }
        });

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