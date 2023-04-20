package com.example.versioapp1.Database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RunningViewModel extends AndroidViewModel {
    private RunningRepository runningRepository;
    LiveData<List<User>> userList;
    LiveData<List<Running>> runningList;
    public RunningViewModel(@NonNull Application application) {
        super(application);
        runningRepository = new RunningRepository(application);
        userList = runningRepository.getUser();
        runningList = runningRepository.getRunning();
    }

    public void insertRunningWithUser(UserWithRunning userWithRunning) {
        runningRepository.insert(userWithRunning);
    }

    public LiveData<List<User>> getAllUser() {
        return userList;
    }

    public LiveData<List<Running>> getAllRunning() {
        return runningList;
    }

}
