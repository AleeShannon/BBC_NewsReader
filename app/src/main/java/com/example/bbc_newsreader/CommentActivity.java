package com.example.bbc_newsreader;

import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

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

/**
 * An activity for users to submit and view comments.
 */
public class CommentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * EditText field for entering the comment text.
     */
    private EditText commentEditText;
    /**
     * Button for submitting the comment.
     */
    private Button submitButton;
    /**
     * List of comments.
     */
    private ArrayList<Comment> commentsList;
    /**
     * Adapter for displaying comments in the list.
     */
    private MyListAdapter myListAdapter;
    /**
     * ListView for displaying the comments.
     */
    private ListView listView;
    /**
     * DrawerLayout for navigation drawer.
     */
    private DrawerLayout drawer;
    /**
     * SharedPreferences used to store and retrieve comments made by users on the comment activity.
     */
    private SharedPreferences sharedPreferences;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
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

    // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String comment = commentEditText.getText().toString();

                Comment newComment = new Comment(comment);
                commentsList.add(newComment);


                myListAdapter.notifyDataSetChanged();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("comment", comment);
                editor.apply();

                Toast.makeText(CommentActivity.this, "Comment submitted", Toast.LENGTH_SHORT).show();

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



    /**
     * Show a Snackbar with a help message.
     */

    private void showSnackbar() {
        Snackbar.make(drawer, R.string.snackbar_help, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item The selected item
     * @return true to display the item as the selected item
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
     * Exit the application.
     */
    private void exitApp() {
        finishAffinity(); // Finish all activities in the app's task stack
        System.exit(0); // Terminate the app process
    }
    /**
     * Show a help dialog with information about using the comment section.
     */
    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Help");
        builder.setMessage(getString(R.string.help_comment));
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
    /**
     * Called when the activity has detected the user's press of the back key.
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
     * Adapter class for displaying comments in a ListView.
     */
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

        /**
         * Constructor for Comment.
         *
         * @param text The text content of the comment.
         */
        public Comment(String text) {
            this.text = text;
        }


        /**
         * Returns the text content of the comment.
         *
         * @return The text content of the comment.
         */
        public String getText() {
            return text;
        }
    }
}