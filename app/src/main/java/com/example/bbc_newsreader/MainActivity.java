package com.example.bbc_newsreader;

import androidx.annotation.NonNull;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * The main activity of the newsreader app, displaying a list of news headlines.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String RSS_FEED_URL = "http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml";
    private ArrayList<Headline> Headlines;
    private FavoritesDB favoritesDb;
    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> links = new ArrayList<>();
    private ArrayList<String> dates = new ArrayList<>();
    private ArrayList<String> descriptions = new ArrayList<>();


    private DrawerLayout drawer;
    ListView listView;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
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

        listView = findViewById(R.id.list_view);
        titles = new ArrayList<>();
        links = new ArrayList<>();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Uri uri = Uri.parse(links.get(position));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        // Initialize favoritesDb
        favoritesDb = new FavoritesDB(this);

        new ProcessInBackground(this).execute(RSS_FEED_URL);
    }

    /**
     * Displays a help Snackbar.
     */
    private void showSnackbar() {
        Snackbar.make(drawer, R.string.snackbar_help, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Called when a navigation item is selected.
     *
     * @param item The selected MenuItem.
     * @return true to display the item as the selected item, false if the item should not be selected.
     */
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
                break;


            case R.id.nav_comment:
                Intent commentIntent = new Intent(this, CommentActivity.class);
                startActivity(commentIntent);
                break;

            case R.id.nav_help:
                showHelpDialog();
                showSnackbar();
                break;

            case R.id.nav_exit:
                exitApp();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /**
     * Exits the app.
     */
    private void exitApp() {
        finishAffinity(); // Finish all activities in the app's task stack
        System.exit(0); // Terminate the app process
    }
    /**
     * Displays a help dialog.
     */
    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Help");
        builder.setMessage(getString(R.string.help_main));
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
    /**
     * Called when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Returns an InputStream for the specified URL.
     *
     * @param url The URL to get the InputStream for.
     * @return The InputStream, or null if an error occurs.
     */
    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }


    /**
     * Gets the FavoritesDB instance.
     *
     * @return The FavoritesDB instance.
     */
    public FavoritesDB getFavoritesDb() {
        return favoritesDb;
    }

    /**
     * Updates the ListView with the given headlines.
     *
     * @param headlines The headlines to display in the ListView.
     */
    public void updateListView(ArrayList<Headline> headlines) {
        ArrayAdapter<Headline> adapter = new HeadlineAdapter(this, headlines, favoritesDb);
        listView.setAdapter(adapter);
    }

}
