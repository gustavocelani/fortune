package com.gcelani.fortune.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gcelani.fortune.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * HomeFragment
 * extends Fragment
 */
public class HomeFragment extends Fragment {

    /**
     * onCreateView
     * @param inflater inflater
     * @param container container
     * @param savedInstanceState savedInstanceState
     * @return View
     */
    @SuppressLint("RestrictedApi")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);

        if (getActivity() != null) {
            FloatingActionButton floatingActionButton = getActivity().findViewById(R.id.fab);
            floatingActionButton.setVisibility(View.INVISIBLE);
        }

        return rootView;
    }

    /**
     * onCreateOptionsMenu
     * @param menu menu
     * @param inflater inflater
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_filter).setVisible(false);
    }
}
