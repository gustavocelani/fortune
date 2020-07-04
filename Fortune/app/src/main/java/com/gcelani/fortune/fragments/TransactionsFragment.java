package com.gcelani.fortune.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gcelani.fortune.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

/**
 * TransactionsFragment
 * extends Fragment
 */
public class TransactionsFragment extends Fragment {

    /**
     * onCreateView
     * @param inflater inflater
     * @param container container
     * @param savedInstanceState savedInstanceState
     * @return View
     */
    @SuppressLint("RestrictedApi")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transactions, container, false);
        setHasOptionsMenu(true);

        if (getActivity() != null) {
            FloatingActionButton floatingActionButton = getActivity().findViewById(R.id.fab);
            floatingActionButton.setVisibility(View.VISIBLE);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "TRANSACTIONS", Snackbar.LENGTH_LONG).setAction("Action", null).show();
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
        menu.findItem(R.id.action_filter).setVisible(true);
    }

    /**
     * onOptionsItemSelected
     * @param item item
     * @return True
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.action_filter) {
            if (getView() != null) {
                Snackbar.make(getView(), "FILTER ACTION", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        }

        return true;
    }
}
