package com.gcelani.fortune.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.gcelani.fortune.utils.Constants;

import java.io.Serializable;

/**
 * Settings
 */
@Entity(tableName = Constants.SETTINGS_TABLE_NAME)
public class Settings implements Serializable {

    /** UID */
    @PrimaryKey()
    public int id;

    /** Language */
    @ColumnInfo(name = "language")
    public String language;

    /** isAuthenticate */
    @ColumnInfo(name = "isAuthenticate")
    public boolean isAuthenticate;
}
