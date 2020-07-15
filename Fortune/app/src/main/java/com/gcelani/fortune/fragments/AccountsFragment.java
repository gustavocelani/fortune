package com.gcelani.fortune.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gcelani.fortune.AccountsActivity;
import com.gcelani.fortune.MainActivity;
import com.gcelani.fortune.R;
import com.gcelani.fortune.adapters.AccountsListAdapter;
import com.gcelani.fortune.model.Account;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * AccountsFragment
 * extends Fragment
 */
public class AccountsFragment extends Fragment {

    /** AccountsListView */
    private ListView mAccountListView;
    /** EmptyLayout */
    private LinearLayout mEmptyLayout;

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
            mAccountListView = rootView.findViewById(R.id.accounts_list_view);
            mEmptyLayout = rootView.findViewById(R.id.empty_layout);

            FloatingActionButton floatingActionButton = getActivity().findViewById(R.id.fab);
            floatingActionButton.show();
            floatingActionButton.setOnClickListener(fabMainOnClickListener);
        }

        return rootView;
    }

    /**
     * onResume
     */
    @Override
    public void onResume() {
        super.onResume();
        updateAccountList();
    }

    /**
     * updateAccountList
     */
    private void updateAccountList() {
        if (getActivity() != null) {
            List<Account> accounts = ((MainActivity) getActivity()).getAppDatabase().accountDao().findAll();

            if (!accounts.isEmpty()) {
                AccountsListAdapter accountsListAdapter = new AccountsListAdapter(getActivity(), accounts);
                mAccountListView.setAdapter(accountsListAdapter);
                mAccountListView.setVisibility(View.VISIBLE);
                mEmptyLayout.setVisibility(View.GONE);

            } else {
                mAccountListView.setVisibility(View.GONE);
                mEmptyLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * fabMainOnClickListener
     */
    private View.OnClickListener fabMainOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), AccountsActivity.class);
            startActivity(intent);
        }
    };

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
