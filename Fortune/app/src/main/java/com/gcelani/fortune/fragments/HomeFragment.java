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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gcelani.fortune.R;
import com.gcelani.fortune.activities.AccountsActivity;
import com.gcelani.fortune.activities.TransactionsActivity;
import com.gcelani.fortune.utils.Constants;
import com.gcelani.fortune.view.FabAnimation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;

/**
 * HomeFragment
 * extends Fragment
 */
public class HomeFragment extends Fragment {

    /** isFabRotate */
    private boolean isFabRotate = false;
    /** FabMain */
    private FloatingActionButton mFabMain;

    /** FabRevenue */
    private LinearLayout mFabRevenueLayout;
    /** FabExpense */
    private LinearLayout mFabExpenseLayout;
    /** FabTransaction */
    private LinearLayout mFabTransactionLayout;

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
            FloatingActionButton fabRevenue = getActivity().findViewById(R.id.fab_revenue);
            fabRevenue.setOnClickListener(fabRevenueOnClickListener);
            mFabRevenueLayout = getActivity().findViewById(R.id.fab_revenue_layout);

            FloatingActionButton fabExpense = getActivity().findViewById(R.id.fab_expense);
            fabExpense.setOnClickListener(fabExpenseOnClickListener);
            mFabExpenseLayout = getActivity().findViewById(R.id.fab_expense_layout);

            FloatingActionButton fabTransaction = getActivity().findViewById(R.id.fab_transaction);
            fabTransaction.setOnClickListener(fabTransactionOnClickListener);
            mFabTransactionLayout = getActivity().findViewById(R.id.fab_transaction_layout);

            mFabMain = getActivity().findViewById(R.id.fab);
            mFabMain.show();
            mFabMain.setOnClickListener(fabMainOnClickListener);
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

    /**
     * fabMainOnClickListener
     */
    private View.OnClickListener fabMainOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            isFabRotate = FabAnimation.rotateFab(view, !isFabRotate);

            if(isFabRotate) {
                FabAnimation.showIn(mFabRevenueLayout);
                FabAnimation.showIn(mFabExpenseLayout);
                FabAnimation.showIn(mFabTransactionLayout);
            } else {
                FabAnimation.showOut(mFabRevenueLayout);
                FabAnimation.showOut(mFabExpenseLayout);
                FabAnimation.showOut(mFabTransactionLayout);
            }
        }
    };

    /**
     * fabRevenueOnClickListener
     */
    private View.OnClickListener fabRevenueOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Snackbar.make(view, "Revenue", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    };

    /**
     * fabExpenseOnClickListener
     */
    private View.OnClickListener fabExpenseOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Snackbar.make(view, "Expense", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    };

    /**
     * fabTransactionOnClickListener
     */
    private View.OnClickListener fabTransactionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), TransactionsActivity.class);
            startActivity(intent);
        }
    };

    /**
     * onDestroyView
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isFabRotate) {
            isFabRotate = FabAnimation.rotateFab(mFabMain, false);
            FabAnimation.showOut(mFabRevenueLayout);
            FabAnimation.showOut(mFabExpenseLayout);
            FabAnimation.showOut(mFabTransactionLayout);
        }
    }
}
