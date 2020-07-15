package com.gcelani.fortune.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.gcelani.fortune.R;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * BalanceTextWatcher
 * implements TextWatcher
 */
public class BalanceTextWatcher implements TextWatcher {

    /** Context */
    private WeakReference<Context> mContext;

    /** Edit Text */
    private final EditText mEditText;
    /** Current Text */
    private String mCurrentText;

    /**
     * BalanceTextWatcher
     * @param editText editText
     * @param context context
     */
    public BalanceTextWatcher(Context context, EditText editText) {
        this.mContext = new WeakReference<>(context);
        this.mEditText = editText;
        this.mCurrentText = mEditText.getText().toString();
    }

    /**
     * onTextChanged
     * @param s string
     * @param start start
     * @param before before
     * @param count count
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(s != null && !s.toString().equals(mCurrentText)) {
            mEditText.removeTextChangedListener(this);
            String formattedText = mCurrentText;

            if (s.toString().length() < 16) {
                try {
                    String cleanString = s.toString().replaceAll("[^\\d]", "");
                    double parsed = (Double.parseDouble(cleanString) / 100);

                    String parsedString = mContext.get().getResources().getString(R.string.money_pattern, parsed);
                    String floatBalancePart = parsedString.split(parsedString.contains(",") ? "," : "\\.")[1];

                    NumberFormat formatter = new DecimalFormat("#,###");
                    String formattedBalance = formatter.format((int) parsed).replaceAll(",", ".") + "," + floatBalancePart;

                    formattedText = mContext.get().getResources().getString(R.string.money_real_pattern, formattedBalance);
                    mCurrentText = formattedText;

                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }

            mEditText.setText(formattedText);
            mEditText.setSelection(formattedText.length());
            mEditText.addTextChangedListener(this);
        }
    }

    /**
     * afterTextChanged
     * @param s editable
     */
    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * beforeTextChanged
     * @param s string
     * @param start start
     * @param count count
     * @param after after
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
}
