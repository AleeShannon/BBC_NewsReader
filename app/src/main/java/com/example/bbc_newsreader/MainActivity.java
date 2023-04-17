package com.example.bbc_newsreader;

import androidx.annotation.NonNull;
import com.example.bbc_newsreader.FavoritesActivity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String RSS_FEED_URL = "http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml";
    private ArrayList<Headline> Headlines;
    private FavoritesDb favoritesDb;
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

        new ProcessInBackground(this).execute(RSS_FEED_URL);
    }
    private void showSnackbar(String message) {
        Snackbar.make(drawer, message, Snackbar.LENGTH_LONG).show();
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
                intent = new Intent(this, FavoritesActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingsFragment()).commit();
                break;

            case R.id.nav_help:
                showHelpDialog();
                showSnackbar("Help clicked");
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Help");
        builder.setMessage("Click a headline for more information, use the button options to open the article in a" +
                "browser or add to favorites");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }
    public FavoritesDb getFavoritesDb() {
        return favoritesDb;
    }

}