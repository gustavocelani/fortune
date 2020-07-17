package com.gcelani.fortune.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.gcelani.fortune.model.Settings;
import com.gcelani.fortune.utils.Constants;

/**
 * SettingsDao
 */
@Dao
public interface SettingsDao {

    @Query("SELECT * FROM " + Constants.SETTINGS_TABLE_NAME + " WHERE id LIKE :id LIMIT 1")
    Settings findById(int id);

    @Update
    void update(Settings settings);

    @Insert
    void insertAll(Settings... settings);
}
