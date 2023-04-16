package com.example.bbc_newsreader;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup fragment_container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, fragment_container, false);
    }
}