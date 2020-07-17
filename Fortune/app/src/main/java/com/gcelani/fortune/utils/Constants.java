package com.gcelani.fortune.utils;

/**
 * Constants
 */
public class Constants {

    /** General */
    public static final String APP_VERSION = "0.0.1";
    public static final    int AUTHENTICATION_INTENT_REQUEST_CODE = 1000;

    /** DataBase */
    public static final String DB_NAME = "fortune_db_1.0";
    public static final String ACCOUNTS_TABLE_NAME = "accounts";
    public static final String SETTINGS_TABLE_NAME = "settings";
    public static final String TRANSACTIONS_TABLE_NAME = "transactions";
    public static final    int SETTINGS_TABLE_ID   = 5132166;

    /** Accounts */
    public static final String ACCOUNT_TAG = "account";

    /** Transactions */
    public enum TRANSACTION_TYPES {
        TRANSACTION,
        REVENUE,
        EXPENSE
    };

    /** Lengths */
    public static final    int ACCOUNT_NAME_MAX_LEN = 30;
    public static final    int TRANSACTION_NAME_MAX_LEN = 30;
    public static final    int TRANSACTION_DESCRIPTION_MAX_LEN = 200;
}
