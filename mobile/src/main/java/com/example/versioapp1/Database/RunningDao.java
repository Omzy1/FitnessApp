package com.example.versioapp1.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;
@Dao
public interface RunningDao {
    @Transaction

    @Insert
    void insertRunning(List<Running> runningList);
    @Insert
    long insertUsers(User users);
    @Query("Select * FROM user")
    LiveData<List<User>> getAllUser();
    @Query("Select * FROM running_activity ORDER BY running_id DESC")
    LiveData<List<Running>> getAllRunningInfo();

}
