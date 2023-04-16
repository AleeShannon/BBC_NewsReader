package com.example.bbc_newsreader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ProcessInBackground extends AsyncTask<String, Void, ArrayList<Headline>> {
    ProgressDialog progressDialog ;
    private MainActivity mActivity;
    Exception exception = null;
    ArrayList<Headline> Headlines;
    ArrayList<String> links;
    ArrayList<String> titles;
    ListView listView;

    public ProcessInBackground(MainActivity activity) {
        mActivity = activity;
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Loading...");
        listView = activity.findViewById(R.id.list_view);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setMessage("Loading Headlines");
        progressDialog.show();
    }

    @Override
    protected ArrayList<Headline> doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            Log.d("MainActivity", "URL: " + strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            try (InputStream inputStream = connection.getInputStream()) {
                XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
                XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
                xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                xmlPullParser.setInput(inputStream, null);
                Headlines = MyParser.parse(xmlPullParser);
                return Headlines;
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Headline> headlines) {
        super.onPostExecute(headlines);
        progressDialog.dismiss();
        if (headlines != null) {
            MyAdapter adapter = new MyAdapter(mActivity.getApplicationContext(), headlines);
            listView.setAdapter(adapter);
            Headlines = headlines;
        } else {
            Toast.makeText(mActivity, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
        }
    }
}