package com.example.bbc_newsreader;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<Headline> headlines;

    public MyAdapter(Context context, ArrayList<Headline> headlines) {
        this.context = context;
        this.headlines = headlines;
    }

    @Override
    public int getCount() {
        return headlines.size();
    }

    @Override
    public Object getItem(int position) {
        return headlines.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);

        TextView titleView = (TextView) rowView.findViewById(R.id.title_TextView);
        TextView dateView = (TextView) rowView.findViewById(R.id.date_TextView);
        TextView descriptionView = (TextView) rowView.findViewById(R.id.description_textView);

        final Headline headline = headlines.get(position);

        titleView.setText(headline.getTitle());
        dateView.setText(headline.getDate());
        descriptionView.setText(headline.getDescription());

        // Hide the date and description initially
        dateView.setVisibility(View.GONE);
        descriptionView.setVisibility(View.GONE);

        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the visibility of the date and description views
                if (dateView.getVisibility() == View.VISIBLE) {
                    dateView.setVisibility(View.GONE);
                    descriptionView.setVisibility(View.GONE);
                } else {
                    dateView.setVisibility(View.VISIBLE);
                    descriptionView.setVisibility(View.VISIBLE);
                }
            }
        });

        Button addFavoriteButton = (Button) rowView.findViewById(R.id.add_favorites_button);
        addFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add the corresponding Headline object to a list of favorites
                FavoriteHeadlines.getInstance(context).addFavorite(headline);
                Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show();
            }
        });


        Button openButton = (Button) rowView.findViewById(R.id.open_article_button);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = headline.getLink();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                intent.putExtra("title", headline.getTitle());
                intent.putExtra("date", headline.getDate());
                intent.putExtra("description", headline.getDescription());
                intent.putExtra("link", headline.getLink());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return rowView;
    }
}


