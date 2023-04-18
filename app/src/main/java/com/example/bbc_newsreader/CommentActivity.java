package com.example.bbc_newsreader;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private EditText commentEditText;
    private Button submitButton;
    private ArrayList<Comment> commentsList;
    private MyListAdapter myListAdapter;
    private ListView listView;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        // Initialize views, adapters, etc.
        commentsList = new ArrayList<>();
        myListAdapter = new MyListAdapter(commentsList, this);

        commentEditText = findViewById(R.id.commentEditText);
        submitButton = findViewById(R.id.submitButton);
        listView = findViewById(R.id.cListView);


        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String comment = commentEditText.getText().toString();

                Comment newComment = new Comment(comment);
                commentsList.add(newComment);


                // Update the ListView adapter to reflect the changes
                myListAdapter.notifyDataSetChanged();

                // Clear the commentEditText after submitting the comment
                commentEditText.setText("");
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }


    private void showSnackbar() {
        Snackbar.make(drawer, R.string.snackbar_help, Snackbar.LENGTH_LONG).show();
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
    private void exitApp() {
        finishAffinity(); // Finish all activities in the app's task stack
        System.exit(0); // Terminate the app process
    }
    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Help");
        builder.setMessage(getString(R.string.help_comment));
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
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

    public class MyListAdapter extends BaseAdapter {

        private ArrayList<Comment> commentsList;
        private LayoutInflater inflater;

        public MyListAdapter(ArrayList<Comment> commentsList, Context context) {
            this.commentsList = commentsList;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return commentsList.size();
        }

        @Override
        public Comment getItem(int position) {
            return commentsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item_comment, parent, false);
            }

            // Bind data to views
            TextView commentTextView = convertView.findViewById(R.id.commentTextView);
            Comment comment = getItem(position);
            commentTextView.setText(comment.getText());

            return convertView;
        }
    }
        public class Comment {
            private String text;

            public Comment(String text) {
                this.text = text;
            }

            public String getText() {
                return text;
            }
        }
    }

