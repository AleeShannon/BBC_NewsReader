package com.example.bbc_newsreader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ProcessInBackground extends AsyncTask<Integer, Void, Exception> {
    ProgressDialog progressDialog ;
    Exception exception = null;
    ArrayList<String> titles;
    ArrayList<String> links;
    ListView listView;

    public ProcessInBackground(ArrayList<String> titles, ArrayList<String> links) {
        this.titles = titles;
        this.links = links;
        this.listView = listView;
    }

    @Override
    protected Exception doInBackground(Integer...params) {
        try {
            URL url = new URL("http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml");

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(url.openConnection().getInputStream(), "UTF_8");

            boolean insideItem = false;
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG) {

                    if (xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = true;
                    } else if (xpp.getName().equalsIgnoreCase("title")) {
                        if (insideItem) {
                            titles.add(xpp.nextText());
                        }
                    } else if (xpp.getName().equalsIgnoreCase("link")) {
                        if (insideItem) {
                            links.add(xpp.nextText());
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                    insideItem = false;
                }
                eventType = xpp.next();
            }

        } catch (MalformedURLException e) {
            exception = e;
        } catch (XmlPullParserException e) {
            exception = e;
        } catch (IOException e) {
            exception = e;
        }
        return exception;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setMessage("Loading Headlines");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Exception s) {
        super.onPostExecute(s);
        MyAdapter adapter = new MyAdapter(listView.getContext(), titles, links);
        listView.setAdapter(adapter);
        progressDialog.dismiss();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = links.get(position);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                listView.getContext().startActivity(i);

            }
        });
        listView.setAdapter(adapter);
        progressDialog.dismiss();

    }
}
