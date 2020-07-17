package com.gcelani.fortune.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.gcelani.fortune.R;
import com.gcelani.fortune.model.Settings;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utils
 */
public class Utils {

    /**
     * parseMoneyStringToDouble
     * @param moneyString moneyString Ex.: R$ 183,52
     * @return Double value Ex.: 183,52
     */
    public static double parseMoneyStringToDouble(String moneyString) {
        double parsed = 0;
        String cleanString = moneyString.replaceAll("[^\\d]", "");

        try {
            parsed = (Double.parseDouble(cleanString) / 100);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        return parsed;
    }

    /**
     * prettyMoneyFormat
     * @param moneyValue balance value Ex.: 1002.03
     * @return Pretty money format Ex.: R$ 1.002,03
     */
    public static String prettyMoneyFormat(Context context, double moneyValue) {
        String parsedString = context.getResources().getString(R.string.money_pattern, moneyValue);
        String floatBalancePart = parsedString.split(parsedString.contains(",") ? "," : "\\.")[1];

        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedBalance = formatter.format((int) moneyValue).replaceAll(",", ".") + "," + floatBalancePart;

        return context.getResources().getString(R.string.money_real_pattern, formattedBalance);
    }

    /**
     * getIconFromAccountType
     * @param context context
     * @param accountType accountType
     * @return Icon ID
     */
    public static int getIconFromAccountType(Context context, String accountType) {
        String[] accountTypes = context.getResources().getStringArray(R.array.account_types);
        List<String> accountTypesArrayList = new ArrayList<>(Arrays.asList(accountTypes));

        int[] accountTypeIcons = {
                R.drawable.ic_money,               // Money
                R.drawable.ic_current_account,     // Current Account
                R.drawable.ic_savings,             // Savings
                R.drawable.ic_investment,          // Low Risk Application
                R.drawable.ic_investment,          // High Risk Application
                R.drawable.ic_investment,          // General Investment
                R.drawable.ic_other                // Other
        };

        if (accountTypesArrayList.indexOf(accountType) >= 0) {
            return accountTypeIcons[accountTypesArrayList.indexOf(accountType)];
        }

        return R.drawable.ic_other;
    }

    /**
     * getAccountTypeSpinnerPosition
     * @param context context
     * @param accountType accountType
     * @return Account Type Spinner Position
     */
    public static int getAccountTypeSpinnerPosition(Context context, String accountType) {
        String[] accountTypes = context.getResources().getStringArray(R.array.account_types);
        List<String> accountTypesArrayList = new ArrayList<>(Arrays.asList(accountTypes));

        if (accountTypesArrayList.indexOf(accountType) >= 0) {
            return accountTypesArrayList.indexOf(accountType);
        }

        return accountTypes.length - 1;
    }

    /**
     * getLanguageSpinnerPosition
     * @param context context
     * @param language language
     * @return Language Spinner Position
     */
    public static int getLanguageSpinnerPosition(Context context, String language) {
        String[] languages = context.getResources().getStringArray(R.array.languages);
        List<String> languagesArrayList = new ArrayList<>(Arrays.asList(languages));
        return Math.max(languagesArrayList.indexOf(language), 0);
    }

    /**
     * generateDefaultSettings
     * @param context context
     * @return Default settings
     */
    public static Settings generateDefaultSettings(Context context) {
        Settings defaultSettings = new Settings();
        defaultSettings.id = Constants.SETTINGS_TABLE_ID;
        defaultSettings.language = context.getResources().getStringArray(R.array.languages)[0];
        defaultSettings.isAuthenticate = false;
        return defaultSettings;
    }
}
