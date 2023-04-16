package com.example.bbc_newsreader;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> links = new ArrayList<>();
    private DrawerLayout drawer;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        listView = (ListView) findViewById(R.id.list_view);
        titles = new ArrayList<String>();
        links = new ArrayList<String>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri uri = Uri.parse(links.get(position));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        ProcessInBackground processInBackground = new ProcessInBackground(titles, links);
        processInBackground.execute();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.nav_favorites:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FavoriteFragment()).commit();
                break;

            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingsFragment()).commit();
                break;

            case R.id.nav_help:
                Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public InputStream getInputStream(URL url){
        try{
            return url.openConnection().getInputStream();
        } catch (IOException e){
            return null;
        }
    }
    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception> {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        Exception exception = null;
        ArrayList<String> titles;
        ArrayList<String> links;

        public ProcessInBackground(ArrayList<String> titles, ArrayList<String> links) {
            this.titles = titles;
            this.links = links;
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
            MyAdapter adapter = new MyAdapter(MainActivity.this, titles, links);
            listView.setAdapter(adapter);
            progressDialog.dismiss();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String url = links.get(position);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
            listView.setAdapter(adapter);
            progressDialog.dismiss();

            }
        }

    }
