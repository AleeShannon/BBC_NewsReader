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

public class MyAdapter extends ArrayAdapter<String> {

    private ArrayList<String> titles;
    private ArrayList<String> links;
    private Context context;

    public MyAdapter(Context context, ArrayList<String> titles, ArrayList<String> links) {
        super(context, R.layout.list_item, titles);
        this.titles = titles;
        this.links = links;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);

        TextView titleView = (TextView) rowView.findViewById(R.id.title_TextView);
        titleView.setText(titles.get(position));

        Button openButton = (Button) rowView.findViewById(R.id.open_article_button);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = links.get(position);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                context.startActivity(intent);
            }
        });

        return rowView;
    }
}

