package com.gcelani.fortune.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.lang.ref.WeakReference;

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
                double parsed = Utils.parseMoneyStringToDouble(s.toString());
                formattedText = Utils.prettyMoneyFormat(mContext.get(), parsed);
                mCurrentText = formattedText;
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
