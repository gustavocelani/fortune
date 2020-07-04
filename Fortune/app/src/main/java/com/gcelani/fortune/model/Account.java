package com.gcelani.fortune.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Account
 */
@Entity
public class Account {

    /** UID */
    @PrimaryKey
    public int id;

    /** Name */
    @ColumnInfo(name = "name")
    public String name;
}
