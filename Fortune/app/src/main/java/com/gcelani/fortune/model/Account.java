package com.gcelani.fortune.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.gcelani.fortune.utils.Constants;

import java.io.Serializable;
import java.util.Date;

/**
 * Account
 */
@Entity(tableName = Constants.ACCOUNTS_TABLE_NAME)
public class Account implements Serializable {

    /** UID */
    @PrimaryKey(autoGenerate = true)
    public int id;

    /** Name */
    @ColumnInfo(name = "name")
    public String name;

    /** Balance */
    @ColumnInfo(name = "balance")
    public double balance;

    /** isPositiveBalance */
    @ColumnInfo(name = "isPositiveBalance")
    public boolean positiveBalance;

    /** Type */
    @ColumnInfo(name = "type")
    public String type;

    /** isInvestment */
    @ColumnInfo(name = "isInvestmentGroup")
    public boolean investmentGroup;

    /** isAvailable */
    @ColumnInfo(name = "isAvailableGroup")
    public boolean availableGroup;

    /** isTotal */
    @ColumnInfo(name = "isTotalGroup")
    public boolean totalGroup;

    /** CreatedAt */
    @ColumnInfo(name = "createdAt")
    public Date createdAt;

    /**
     * isValid
     * @return True if isValid
     */
    public boolean isValid() {
        return (name != null && !name.isEmpty() && name.length() <= Constants.ACCOUNT_NAME_MAX_LEN);
    }
}
