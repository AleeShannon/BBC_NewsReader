package com.example.bbc_newsreader;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<Headline> headlines;
    private ArrayList<Favorite> favorites = new ArrayList<>();

    public MyAdapter(Context context, ArrayList<Headline> headlines) {
        this.context = context;
        this.headlines = headlines;
        this.favorites = favorites;
      ;
    }
    public void setHeadlines(ArrayList<Headline> favorite) {
        this.favorites.clear();
        this.favorites.addAll(favorites);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return headlines.size();
    }

    @Override
    public Object getItem(int position) {
        if (position < headlines.size()) {
            return headlines.get(position);
        } else {
            return favorites.get(position - headlines.size());
        }
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

        // initialize favorite button
        Button addFavoriteButton = (Button) rowView.findViewById(R.id.add_favorites_button);
        addFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Download the headline and store it as a Favorite object
                HeadlineDownloader downloader = new HeadlineDownloader(context);
                downloader.downloadHeadline(headline.getLink(), new HeadlineDownloader.Callback() {
                    @Override
                    public void onHeadlineDownloaded(Headline downloadedHeadline) {
                        Favorite favorite = new Favorite(downloadedHeadline.getTitle(),
                                downloadedHeadline.getDate(), downloadedHeadline.getDescription(),
                                downloadedHeadline.getLink());

                        // Add the corresponding Favorite object to the list of favorites
                        favorites.add(favorite);

                        Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDownloadFailed() {
                        Toast.makeText(context, "Failed to download headline", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        // initialize open article button and onCLick
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


