package com.gcelani.fortune.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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

import java.util.Calendar;

/**
 * TransactionsActivity
 * extends AppCompatActivity
 */
public class TransactionsActivity extends AppCompatActivity {

    /** Activity */
    private Activity mActivity;

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
    /** DateTextView */
    private TextView mDateTextView;

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
        mActivity = this;

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
        mDateTextView = findViewById(R.id.date_text_view);

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

        Calendar calendar = Utils.getCalendar(0,0,0);
        mDateTextView.setText(getResources().getString(R.string.date_pattern,
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH)+1,
                calendar.get(Calendar.YEAR)));
        mDateTextView.setOnClickListener(onDateClickListener);
    }

    /**
     * onDateClickListener
     */
    private View.OnClickListener onDateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int[] parsedDate = Utils.parseDateFormat(mDateTextView.getText().toString());
            Calendar calendar = Utils.getCalendar(0, 0, 0);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    mActivity,
                    onDateSetListener,
                    parsedDate[2] > 0 ? parsedDate[2] : calendar.get(Calendar.YEAR),
                    parsedDate[1] > 0 ? parsedDate[1] : calendar.get(Calendar.MONTH),
                    parsedDate[0] > 0 ? parsedDate[0] : calendar.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.show();
        }

        /**
         * onDateSetListener
         */
        private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mDateTextView.setText(getResources().getString(R.string.date_pattern, dayOfMonth, monthOfYear, year));
            }
        };
    };

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
