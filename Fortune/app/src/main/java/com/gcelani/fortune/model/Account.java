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

    /** Balance */
    @ColumnInfo(name = "balance")
    public double balance;

    /** isPositiveBalance */
    @ColumnInfo(name = "positiveBalance")
    public boolean positiveBalance;

    /** Type */
    @ColumnInfo(name = "type")
    public int type;

    /** isInvestment */
    @ColumnInfo(name = "investment")
    public boolean investment;

    /** isAvailable */
    @ColumnInfo(name = "available")
    public boolean available;

    /** isTotal */
    @ColumnInfo(name = "total")
    public boolean total;
}
