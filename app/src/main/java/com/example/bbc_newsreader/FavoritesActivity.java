package com.example.bbc_newsreader;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Headline> favoriteHeadlines;
    private MyAdapter adapter;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        // Get reference to the list view
        ListView fListView = findViewById(R.id.list_view_favorites);
        // Load the favorite headlines from shared preferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout2);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

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
                    break;
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        private void showHelpDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Help");
            builder.setMessage("Save items to this page by clicking Add to Favorites, delete saved items " +
                    "by clicking delete");
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
    }






