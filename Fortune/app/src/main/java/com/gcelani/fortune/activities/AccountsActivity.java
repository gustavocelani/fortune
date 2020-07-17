package com.gcelani.fortune.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.gcelani.fortune.R;
import com.gcelani.fortune.database.AppDatabase;
import com.gcelani.fortune.model.Account;
import com.gcelani.fortune.utils.BalanceTextWatcher;
import com.gcelani.fortune.utils.Constants;
import com.gcelani.fortune.utils.Utils;

import java.util.Date;

/**
 * AccountActivity
 * extends AppCompatActivity
 */
public class AccountsActivity extends AppCompatActivity {

    /** BalanceEditText */
    private EditText mBalanceEditText;
    /** PositiveBalanceSwitch */
    private Switch mPositiveBalanceSwitch;
    /** NameEditText */
    private EditText mNameEditText;
    /** AccountTypeSpinner */
    private Spinner mAccountTypeSpinner;
    /** IsAvailableCheckBox */
    private CheckBox mIsAvailableCheckBox;
    /** IsInvestmentCheckBox */
    private CheckBox mIsInvestmentCheckBox;
    /** IsTotalCheckBox */
    private CheckBox mIsTotalCheckBox;

    /** AppDatabase */
    private AppDatabase mAppDatabase;

    /** EditingAccount */
    private Account mEditingAccount;

    /**
     * onCreate
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
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

        mEditingAccount = getIntent().hasExtra(Constants.ACCOUNT_TAG)
                ? (Account) getIntent().getSerializableExtra(Constants.ACCOUNT_TAG)
                : null;

        initializeUiElements();
    }

    /**
     * initializeUiElements
     */
    private void initializeUiElements() {
        mBalanceEditText = findViewById(R.id.account_balance);
        mPositiveBalanceSwitch = findViewById(R.id.positive_balance_switch);
        mNameEditText = findViewById(R.id.account_name);
        mIsAvailableCheckBox = findViewById(R.id.account_is_available);
        mIsInvestmentCheckBox = findViewById(R.id.account_is_investment);
        mIsTotalCheckBox = findViewById(R.id.account_is_total);
        mAccountTypeSpinner = findViewById(R.id.account_type);

        mAccountTypeSpinner.setAdapter(
                new ArrayAdapter<>(this,
                        R.layout.support_simple_spinner_dropdown_item,
                        getResources().getStringArray(R.array.account_types)));

        mNameEditText.setText(isEditing() ? mEditingAccount.name : "");
        mAccountTypeSpinner.setSelection(isEditing() ? Utils.getAccountTypeSpinnerPosition(this, mEditingAccount.category) : 0);
        mIsAvailableCheckBox.setChecked(isEditing() && mEditingAccount.availableGroup);
        mIsInvestmentCheckBox.setChecked(isEditing() && mEditingAccount.investmentGroup);
        mIsTotalCheckBox.setChecked(isEditing() && mEditingAccount.totalGroup);

        mBalanceEditText.addTextChangedListener(new BalanceTextWatcher(this, mBalanceEditText));
        mBalanceEditText.setText(isEditing() ? Utils.prettyMoneyFormat(this, mEditingAccount.balance) : "0");

        mPositiveBalanceSwitch.setOnCheckedChangeListener(positiveBalanceSwitchOnCheckedChangeListener);
        mPositiveBalanceSwitch.setChecked(!isEditing() || mEditingAccount.positiveBalance);
        updateUiColor(!isEditing() || mEditingAccount.positiveBalance);
    }

    /**
     * positiveBalanceSwitchOnCheckedChangeListener
     */
    private CompoundButton.OnCheckedChangeListener positiveBalanceSwitchOnCheckedChangeListener =
            new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            updateUiColor(isChecked);
        }
    };

    /**
     * updateUiColor
     * @param isEditing isEditing
     */
    private void updateUiColor(boolean isEditing) {
        int color = getResources().getColor(isEditing ? R.color.green : R.color.red);
        mPositiveBalanceSwitch.getThumbDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        mBalanceEditText.setTextColor(color);
        mNameEditText.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mIsAvailableCheckBox.setButtonTintList(ColorStateList.valueOf(color));
            mIsInvestmentCheckBox.setButtonTintList(ColorStateList.valueOf(color));
            mIsTotalCheckBox.setButtonTintList(ColorStateList.valueOf(color));
        }
    }

    /**
     * saveAccount
     * Create Account object
     * Save/Update Account object on DB
     */
    private void saveAccount() {
        Account account = new Account();

        account.balance = Utils.parseMoneyStringToDouble(mBalanceEditText.getText().toString());
        account.positiveBalance = mPositiveBalanceSwitch.isChecked();
        account.name = mNameEditText.getText().toString().trim();
        account.category = mAccountTypeSpinner.getSelectedItem().toString();
        account.availableGroup = mIsAvailableCheckBox.isChecked();
        account.investmentGroup = mIsInvestmentCheckBox.isChecked();
        account.totalGroup = mIsTotalCheckBox.isChecked();

        if (!account.isValid()) {
            final AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_warning)
                    .setTitle(getResources().getString(R.string.account_invalid))
                    .setMessage(getResources().getString(R.string.invalid_data_message))
                    .setPositiveButton(getResources().getString(R.string.ok_text), null)
                    .create();
            alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface arg0) {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.white));
                }
            });

            alertDialog.show();
            return;
        }

        if (isEditing()) {
            account.id = mEditingAccount.id;
            mAppDatabase.accountDao().update(account);
        } else {
            account.createdAt = new Date();
            mAppDatabase.accountDao().insertAll(account);
        }

        finish();
    }

    /**
     * deleteAccount
     */
    private void deleteAccount() {
        if (isEditing()) {
            final AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_delete)
                    .setTitle(getResources().getString(R.string.account_delete_confirm_title))
                    .setMessage(getResources().getString(R.string.confirm_question))
                    .setPositiveButton(getResources().getString(R.string.yes_text), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAppDatabase.accountDao().delete(mEditingAccount);
                            finish();
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.no_text), null)
                    .create();

            alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface arg0) {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.white));
                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.white));
                }
            });

            alertDialog.show();
        }
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
                saveAccount();
                break;

            case R.id.action_delete:
                deleteAccount();
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
        return mEditingAccount != null;
    }
}
