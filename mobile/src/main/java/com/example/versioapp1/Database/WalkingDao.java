package com.example.versioapp1.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface WalkingDao {
    @Transaction

    @Insert
    void insertWalking(List<Walking> walking);
    @Insert
    long insertUsers(User users);
    @Query("Select * FROM user")
    LiveData<List<User>> getAllUser();
    @Query("Select * FROM walking_activity ORDER BY walking_id DESC")
    LiveData<List<Walking>> getAllWalkingActivityInfo();
    @Query("DELETE FROM  user")
    void deleteAll();
}
