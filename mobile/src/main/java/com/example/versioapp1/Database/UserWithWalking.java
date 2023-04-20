package com.example.versioapp1.Database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithWalking {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "user_id",
            entityColumn = "user_fid"
    )
    public List<Walking> walkingList;

    public UserWithWalking(User user,List<Walking>walkings){
        this.user = user;
        this.walkingList = walkings;
    }

}
