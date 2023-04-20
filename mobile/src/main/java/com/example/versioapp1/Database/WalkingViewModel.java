package com.example.versioapp1.Database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WalkingViewModel extends AndroidViewModel {
    private WalkingRepository walkingRepository;
    LiveData<List<User>> userList;
    LiveData<List<Walking>> walkingList;
    public WalkingViewModel(@NonNull Application application) {
        super(application);
        walkingRepository = new WalkingRepository(application);
        userList = walkingRepository.getUser();
        walkingList = walkingRepository.getWalking();
    }

    public void insertWalkingWithUser(UserWithWalking userWithWalking) {
        walkingRepository.insert(userWithWalking);
    }

    public LiveData<List<User>> getAllUser() {
        return userList;
    }

    public LiveData<List<Walking>> getAllWalking() {
        return walkingList;
    }

    public void deleteAll(){walkingRepository.deleteAll();}
}

