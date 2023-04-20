package com.example.versioapp1.Database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithRunning {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "user_id",
            entityColumn = "user_fId"
    )
    public List<Running> runningList;

    public UserWithRunning(User user,List<Running>runnings){
        this.user = user;
        this.runningList = runnings;
    }
}
