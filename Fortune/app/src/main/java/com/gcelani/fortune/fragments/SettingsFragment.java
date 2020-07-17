package com.gcelani.fortune.fragments;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gcelani.fortune.activities.MainActivity;
import com.gcelani.fortune.R;
import com.gcelani.fortune.model.Settings;
import com.gcelani.fortune.utils.Constants;
import com.gcelani.fortune.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * SettingsFragment
 * extends Fragment
 */
public class SettingsFragment extends Fragment {

    /** Language Spinner */
    private Spinner mLanguageSpinner;
    /** AuthenticationSwitch */
    private Switch mAuthenticationSwitch;

    /** Settings */
    private Settings mSettings;

    /**
     * onCreateView
     * @param inflater inflater
     * @param container container
     * @param savedInstanceState savedInstanceState
     * @return View
     */
    @SuppressLint("RestrictedApi")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        setHasOptionsMenu(true);

        if (getActivity() != null) {
            FloatingActionButton floatingActionButton = getActivity().findViewById(R.id.fab);
            floatingActionButton.hide();

            mLanguageSpinner = rootView.findViewById(R.id.settings_language_spinner);
            mAuthenticationSwitch = rootView.findViewById(R.id.settings_authenticate_switch);

            mSettings = ((MainActivity) getActivity()).getAppDatabase().settingsDao().findById(Constants.SETTINGS_TABLE_ID);
            initializeUiElements();
        }

        return rootView;
    }

    /**
     * initializeUiElements
     */
    private void initializeUiElements() {
        if (getActivity() != null) {
            mLanguageSpinner.setAdapter(
                    new ArrayAdapter<>(getActivity(),
                            R.layout.support_simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.languages)));

            mLanguageSpinner.setOnItemSelectedListener(onLanguageSpinnerSelectedListener);
            mLanguageSpinner.setSelection(Utils.getLanguageSpinnerPosition(getActivity(), mSettings.language));

            mAuthenticationSwitch.setOnCheckedChangeListener(onAuthenticationSwitchOnCheckedChangeListener);
            mAuthenticationSwitch.setChecked(mSettings.isAuthenticate);
            updateUiColor();
        }
    }

    /**
     * onLanguageSpinnerSelectedListener
     */
    private AdapterView.OnItemSelectedListener onLanguageSpinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            String selectedLanguage = getResources().getStringArray(R.array.languages)[position];

            if (!mSettings.language.equals(selectedLanguage)) {
                mSettings.language = selectedLanguage;
                updateSettings();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    /**
     * onAuthenticationSwitchOnCheckedChangeListener
     */
    private CompoundButton.OnCheckedChangeListener onAuthenticationSwitchOnCheckedChangeListener =
            new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    int color = isChecked ? getResources().getColor(R.color.green) : getResources().getColor(R.color.red);
                    mAuthenticationSwitch.getThumbDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);

                    if(mSettings.isAuthenticate != isChecked) {
                        mSettings.isAuthenticate = isChecked;
                        updateSettings();
                    }
                }
            };

    /**
     * updateUiColor
     */
    private void updateUiColor() {
        mAuthenticationSwitch.getThumbDrawable().setColorFilter(
                mSettings.isAuthenticate
                        ? getResources().getColor(R.color.green)
                        : getResources().getColor(R.color.red),
                PorterDuff.Mode.MULTIPLY);
    }

    /**
     * updateSettings
     */
    private void updateSettings() {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).getAppDatabase().settingsDao().update(mSettings);
        }
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
}
