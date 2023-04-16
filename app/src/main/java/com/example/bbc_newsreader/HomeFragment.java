package com.example.bbc_newsreader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

        private ListView listView;
        private ArrayList<String> titles = new ArrayList<>();
        private ArrayList<String> links = new ArrayList<>();

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_home, container, false);
            listView = view.findViewById(R.id.list_view);
            return view;
        }

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
