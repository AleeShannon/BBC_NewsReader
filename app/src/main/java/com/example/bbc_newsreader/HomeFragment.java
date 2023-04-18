package com.example.bbc_newsreader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * A fragment representing the home screen of the newsreader app.
 */
public class HomeFragment extends Fragment {

        private ListView listView;
        private ArrayList<String> titles = new ArrayList<>();
        private ArrayList<String> links = new ArrayList<>();

        /**
         * Creates and returns the view hierarchy associated with the fragment.
         *
         * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
         * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
         * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
         * @return The View for the fragment's UI, or null.
         */
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_home, container, false);
            listView = view.findViewById(R.id.list_view);
            return view;
        }

        // Getter and setter methods for HomeFragment
        public ListView getListView() {
            return listView;
        }

        public ArrayList<String> getTitles() {
            return titles;
        }

        public ArrayList<String> getLinks() {
            return links;
        }

        public void setTitles(ArrayList<String> titles) {
            this.titles = titles;
        }

        public void setLinks(ArrayList<String> links) {
            this.links = links;
        }
    }
