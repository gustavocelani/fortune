package com.gcelani.fortune.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.gcelani.fortune.R;
import com.gcelani.fortune.database.AppDatabase;
import com.gcelani.fortune.model.Transaction;
import com.gcelani.fortune.utils.BalanceTextWatcher;
import com.gcelani.fortune.utils.Constants;
import com.gcelani.fortune.utils.Utils;

/**
 * TransactionsActivity
 * extends AppCompatActivity
 */
public class TransactionsActivity extends AppCompatActivity {

    /** ValueEditText */
    private EditText mValueEditText;
    /** SourceAccountSpinner */
    private Spinner mSourceAccountSpinner;
    /** TargetAccountSpinner */
    private Spinner mTargetAccountSpinner;
    /** NameEditText */
    private EditText mNameEditText;
    /** DescriptionEditText */
    private EditText mDescriptionEditText;

    /** AppDatabase */
    private AppDatabase mAppDatabase;

    /** Account Names */
    private String[] mAccountNames;

    /** mEditingTransaction */
    private Transaction mEditingTransaction;

    /**
     * onCreate
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mAppDatabase = Room.databaseBuilder(
                this,
                AppDatabase.class,
                Constants.DB_NAME)
                .allowMainThreadQueries()
                .build();

        mAccountNames = mAppDatabase.accountDao().getAccountNames();
        if (mAccountNames.length < 2) {
            final AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_warning)
                    .setTitle(getResources().getString(R.string.transaction_text))
                    .setMessage(getResources().getString(R.string.empty_accounts))
                    .setPositiveButton(getResources().getString(R.string.ok_text), null)
                    .create();
            alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface arg0) {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.white));
                }
            });
            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    finish();
                }
            });

            alertDialog.show();
        }

        mEditingTransaction = getIntent().hasExtra(Constants.TRANSACTION_TAG)
                ? (Transaction) getIntent().getSerializableExtra(Constants.TRANSACTION_TAG)
                : null;

        initializeUiElements();
    }

    /**
     * initializeUiElements
     */
    private void initializeUiElements() {
        mValueEditText = findViewById(R.id.value);
        mSourceAccountSpinner = findViewById(R.id.source_account_spinner);
        mTargetAccountSpinner = findViewById(R.id.target_account_spinner);
        mNameEditText = findViewById(R.id.name);
        mDescriptionEditText = findViewById(R.id.description);

        mValueEditText.addTextChangedListener(new BalanceTextWatcher(this, mValueEditText));
        mValueEditText.setText(isEditing() ? Utils.prettyMoneyFormat(this, mEditingTransaction.value) : "0");

        mSourceAccountSpinner.setAdapter(
                new ArrayAdapter<>(this,
                        R.layout.support_simple_spinner_dropdown_item,
                        mAccountNames));

        mTargetAccountSpinner.setAdapter(
                new ArrayAdapter<>(this,
                        R.layout.support_simple_spinner_dropdown_item,
                        mAccountNames));

        mNameEditText.setText(isEditing() ? mEditingTransaction.name : "");
        mDescriptionEditText.setText(isEditing() ? mEditingTransaction.description : "");
    }

    /**
     * onOptionsItemSelected
     * @param item item
     * @return Option result
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.action_save:
//                save();
                break;

            case R.id.action_delete:
//                delete();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * onCreateOptionsMenu
     * @param menu menu
     * @return True
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_delete, menu);
        menu.findItem(R.id.action_delete).setVisible(isEditing());
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * isEditing
     * @return True if is editing
     */
    private boolean isEditing() {
        return mEditingTransaction != null;
    }
}
