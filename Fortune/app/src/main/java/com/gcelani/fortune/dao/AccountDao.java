package com.gcelani.fortune.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.gcelani.fortune.model.Account;
import com.gcelani.fortune.utils.Constants;

import java.util.List;

/**
 * AccountDao
 */
@Dao
public interface AccountDao {

    @Query("SELECT * FROM " + Constants.ACCOUNTS_TABLE_NAME)
    List<Account> findAll();

    @Query("SELECT * FROM " + Constants.ACCOUNTS_TABLE_NAME + " WHERE id IN (:ids)")
    List<Account> loadAllByIds(int[] ids);

    @Query("SELECT * FROM " + Constants.ACCOUNTS_TABLE_NAME + " WHERE id LIKE :id LIMIT 1")
    Account findById(int id);

    @Update
    void update(Account account);

    @Insert
    void insertAll(Account... accounts);

    @Delete
    void delete(Account account);
}
