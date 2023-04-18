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

public class FavoritesAdapter extends ArrayAdapter<Headline> {

    private final Context context;
    private final ArrayList<Headline> headlines;
    private final FavoritesDB favoritesDb;

    public FavoritesAdapter(Context context, ArrayList<Headline> headlines, FavoritesDB favoritesDb) {
        super(context, R.layout.list_item, headlines);
        this.context = context;
        this.headlines = headlines;
        this.favoritesDb = favoritesDb;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.titleTextView = convertView.findViewById(R.id.title_TextView);
            viewHolder.dateTextView = convertView.findViewById(R.id.date_TextView);
            viewHolder.descriptionTextView = convertView.findViewById(R.id.description_textView);
            viewHolder.linkTextView = convertView.findViewById(R.id.link_textView);
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

    private static class ViewHolder {
        TextView titleTextView;
        TextView dateTextView;
        TextView descriptionTextView;
        TextView linkTextView;
        Button openArticleButton;
        Button favoriteButton;
    }
}
