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

import java.util.ArrayList;
/**
 * Custom ArrayAdapter for displaying favorite headlines.
 */
public class FavoritesAdapter extends ArrayAdapter<Headline> {

    /**
     * The context used to inflate the layout.
     */
    private final Context context;
    /**
     * The list of headlines to be displayed.
     */
    private final ArrayList<Headline> headlines;
    /**
     * The database used for managing favorites.
     */
    private final FavoritesDB favoritesDb;

    /**
     * Constructor for the FavoritesAdapter.
     *
     * @param context    The context used to inflate the layout.
     * @param headlines  The list of headlines to be displayed.
     * @param favoritesDb The database used for managing favorites.
     */
    public FavoritesAdapter(Context context, ArrayList<Headline> headlines, FavoritesDB favoritesDb) {
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.titleTextView = convertView.findViewById(R.id.title_TextView);
            viewHolder.dateTextView = convertView.findViewById(R.id.date_TextView);
            viewHolder.descriptionTextView = convertView.findViewById(R.id.description_textView);
            viewHolder.linkTextView = convertView.findViewById(R.id.link_TextView);
            viewHolder.openArticleButton = convertView.findViewById(R.id.open_article_button);
            viewHolder.favoriteButton = convertView.findViewById(R.id.add_favorites_button);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Headline headline = headlines.get(position);
        viewHolder.titleTextView.setText(headline.getTitle());
        viewHolder.dateTextView.setText(headline.getDate());
        viewHolder.descriptionTextView.setText(headline.getDescription());
        viewHolder.linkTextView.setText(headline.getLink());

        viewHolder.openArticleButton.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(headline.getLink()));
            context.startActivity(intent);
        });

        viewHolder.favoriteButton.setText("Unfavorite");
        viewHolder.favoriteButton.setOnClickListener(view -> {
            favoritesDb.deleteFavorite(headline.getLink());
            headlines.remove(position);
            notifyDataSetChanged();
        });

        return convertView;
    }

    /**
     * ViewHolder pattern to improve performance of the adapter.
     */
    private static class ViewHolder {
        TextView titleTextView;
        TextView dateTextView;
        TextView descriptionTextView;
        TextView linkTextView;
        Button openArticleButton;
        Button favoriteButton;
    }
}
