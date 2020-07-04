package com.gcelani.fortune.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.gcelani.fortune.dao.AccountDao;
import com.gcelani.fortune.model.Account;

/**
 * AppDatabase
 * extends RoomDatabase
 */
@Database(entities = {Account.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    /** AccountDao */
    public abstract AccountDao accountDao();
}
