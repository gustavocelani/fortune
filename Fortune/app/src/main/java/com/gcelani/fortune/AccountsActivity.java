package com.gcelani.fortune;

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

import com.gcelani.fortune.utils.BalanceTextWatcher;

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

        initializeUiElementsForNewAccount();
    }

    /**
     * initializeUiElementsForNewAccount
     */
    private void initializeUiElementsForNewAccount() {
        mBalanceEditText = findViewById(R.id.account_balance);
        mBalanceEditText.addTextChangedListener(new BalanceTextWatcher(this, mBalanceEditText));
        mBalanceEditText.setText("0");

        mNameEditText = findViewById(R.id.account_name);
        mIsAvailableCheckBox = findViewById(R.id.account_is_available);
        mIsInvestmentCheckBox = findViewById(R.id.account_is_investment);
        mIsTotalCheckBox = findViewById(R.id.account_is_total);

        mAccountTypeSpinner = findViewById(R.id.account_type);
        mAccountTypeSpinner.setAdapter(
                new ArrayAdapter<>(this,
                        R.layout.support_simple_spinner_dropdown_item,
                        getResources().getStringArray(R.array.account_types)));

        mPositiveBalanceSwitch = findViewById(R.id.positive_balance_switch);
        mPositiveBalanceSwitch.setOnCheckedChangeListener(positiveBalanceSwitchOnCheckedChangeListener);
        mPositiveBalanceSwitch.setChecked(true);
    }

    /**
     * positiveBalanceSwitchOnCheckedChangeListener
     */
    private CompoundButton.OnCheckedChangeListener positiveBalanceSwitchOnCheckedChangeListener =
            new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            int newColor = getResources().getColor(isChecked ? R.color.green : R.color.red);
            mPositiveBalanceSwitch.getThumbDrawable().setColorFilter(newColor, PorterDuff.Mode.MULTIPLY);
            mBalanceEditText.setTextColor(newColor);
            mNameEditText.getBackground().setColorFilter(newColor, PorterDuff.Mode.SRC_IN);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mIsAvailableCheckBox.setButtonTintList(ColorStateList.valueOf(newColor));
                mIsInvestmentCheckBox.setButtonTintList(ColorStateList.valueOf(newColor));
                mIsTotalCheckBox.setButtonTintList(ColorStateList.valueOf(newColor));
            }
        }
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
                break;

            case R.id.action_delete:
                break;

            default:
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
        getMenuInflater().inflate(R.menu.accounts, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
