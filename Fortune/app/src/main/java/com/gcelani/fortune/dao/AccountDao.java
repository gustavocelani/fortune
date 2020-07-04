package com.gcelani.fortune.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.gcelani.fortune.model.Account;

import java.util.List;

/**
 * AccountDao
 */
@Dao
public interface AccountDao {

    @Query("SELECT * FROM account")
    List<Account> getAll();

    @Query("SELECT * FROM account WHERE id IN (:ids)")
    List<Account> loadAllByIds(int[] ids);

    @Query("SELECT * FROM account WHERE name LIKE :name LIMIT 1")
    Account findByName(String name);

    @Insert
    void insertAll(Account... accounts);

    @Delete
    void delete(Account account);
}
