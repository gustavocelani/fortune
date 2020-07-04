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
import com.google.android.material.snackbar.Snackbar;

/**
 * AccountsFragment
 * extends Fragment
 */
public class AccountsFragment extends Fragment {

    /**
     * onCreateView
     * @param inflater inflater
     * @param container container
     * @param savedInstanceState savedInstanceState
     * @return View
     */
    @SuppressLint("RestrictedApi")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_accounts, container, false);
        setHasOptionsMenu(true);

        if (getActivity() != null) {
            FloatingActionButton floatingActionButton = getActivity().findViewById(R.id.fab);
            floatingActionButton.setVisibility(View.VISIBLE);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "ACCOUNTS", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            });
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
