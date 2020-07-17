package com.gcelani.fortune.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.gcelani.fortune.dao.AccountDao;
import com.gcelani.fortune.dao.SettingsDao;
import com.gcelani.fortune.model.Account;
import com.gcelani.fortune.model.Settings;
import com.gcelani.fortune.model.Transaction;

/**
 * AppDatabase
 * extends RoomDatabase
 */
@Database(
        entities = {
                Account.class,
                Settings.class,
                Transaction.class
        },
        version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    /** AccountDao */
    public abstract AccountDao accountDao();

    /** SettingsDao */
    public abstract SettingsDao settingsDao();
}
