package com.gcelani.fortune.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.gcelani.fortune.utils.Constants;

import java.io.Serializable;
import java.util.Date;

/**
 * Transaction
 */
@Entity(tableName = Constants.TRANSACTIONS_TABLE_NAME)
public class Transaction implements Serializable {

    /** UID */
    @PrimaryKey(autoGenerate = true)
    public int id;

    /** Name */
    @ColumnInfo(name = "name")
    public String name;

    /** Value */
    @ColumnInfo(name = "value")
    public double value;

    /** Date */
    @ColumnInfo(name = "date")
    public Date date;

    /**
     * Type
     * - Revenue
     * - Expense
     * - Transaction
     *
     * Internal management
     */
    @ColumnInfo(name = "type")
    public String type;

    /** Category */
    @ColumnInfo(name = "category")
    public String category;

    /** Description */
    @ColumnInfo(name = "description")
    public String description;

    /**
     * Source AccountID
     * Only available for transaction type
     */
    @ColumnInfo(name = "sourceAccountId")
    public int sourceAccountId;

    /** Target AccountID */
    @ColumnInfo(name = "targetAccountId")
    public int targetAccountId;

    /**
     * isCompleted
     * Only available for: revenue/expense types
     */
    @ColumnInfo(name = "isCompleted")
    public boolean completed;

    /** CreatedAt */
    @ColumnInfo(name = "createdAt")
    public Date createdAt;

    /**
     * isValid
     * @return True if isValid
     */
    public boolean isValid() {
        boolean valid = (name != null && !name.isEmpty() && name.length() <= Constants.TRANSACTION_NAME_MAX_LEN);

        if (description != null) {
            valid = valid && (description.length() <= Constants.TRANSACTION_DESCRIPTION_MAX_LEN);
        }

        if (type.equals(String.valueOf(Constants.TRANSACTION_TYPES.TRANSACTION))) {
            valid = valid && (value > 0);
        }

        return valid;
    }
}
