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
import com.gcelani.fortune.view.ViewAnimation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

/**
 * TransactionsFragment
 * extends Fragment
 */
public class TransactionsFragment extends Fragment {

    /** isFabRotate */
    private boolean isFabRotate = false;
    /** FabMain */
    private FloatingActionButton mFabMain;

    /** FabRevenue */
    private FloatingActionButton mFabRevenue;
    /** FabExpense */
    private FloatingActionButton mFabExpense;
    /** FabTransaction */
    private FloatingActionButton mFabTransaction;

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
            mFabRevenue = getActivity().findViewById(R.id.fab_revenue);
            mFabRevenue.setOnClickListener(fabRevenueOnClickListener);
            ViewAnimation.init(mFabRevenue);

            mFabExpense = getActivity().findViewById(R.id.fab_expense);
            mFabExpense.setOnClickListener(fabExpenseOnClickListener);
            ViewAnimation.init(mFabExpense);

            mFabTransaction = getActivity().findViewById(R.id.fab_transaction);
            mFabTransaction.setOnClickListener(fabTransactionOnClickListener);
            ViewAnimation.init(mFabTransaction);

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
        menu.findItem(R.id.action_filter).setVisible(true);
    }

    /**
     * onOptionsItemSelected
     * @param item item
     * @return True
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            if (getView() != null) {
                Snackbar.make(getView(), "FILTER ACTION", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * fabMainOnClickListener
     */
    private View.OnClickListener fabMainOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            isFabRotate = ViewAnimation.rotateFab(view, !isFabRotate);
            if(isFabRotate){
                ViewAnimation.showIn(mFabRevenue);
                ViewAnimation.showIn(mFabExpense);
                ViewAnimation.showIn(mFabTransaction);
            }else{
                ViewAnimation.showOut(mFabRevenue);
                ViewAnimation.showOut(mFabExpense);
                ViewAnimation.showOut(mFabTransaction);
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
            Snackbar.make(view, "Transaction", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    };

    /**
     * onDetach
     */
    @Override
    public void onDetach() {
        super.onDetach();
        if (isFabRotate) {
            isFabRotate = ViewAnimation.rotateFab(mFabMain, false);
            ViewAnimation.showOut(mFabRevenue);
            ViewAnimation.showOut(mFabExpense);
            ViewAnimation.showOut(mFabTransaction);
        }
    }
}
