package com.example.versioapp1.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RunningRepository {
    private RunningDao runningDao;

    public RunningRepository(Application application){
        MyDatabase database = MyDatabase.getDatabase(application);
        runningDao = database.runningDao();
    }
    LiveData<List<User>> getUser(){
        return runningDao.getAllUser();
    }
    LiveData<List<Running>> getRunning(){return  runningDao.getAllRunningInfo();}

    public void insert(UserWithRunning userWithRunning){
        new insertAsync(runningDao).execute(userWithRunning);
    }
    private static class insertAsync extends AsyncTask<UserWithRunning,Void,Void> {
        private RunningDao runningDaoAsyn;
        insertAsync(RunningDao runningDao){
            runningDaoAsyn = runningDao;
        }

        @Override
        protected Void doInBackground(UserWithRunning... userWithRunnings) {
            long identifier = runningDaoAsyn.insertUsers(userWithRunnings[0].user);
            for(Running running: userWithRunnings[0].runningList){
                running.setUserId(identifier);
            }
            runningDaoAsyn.insertRunning(userWithRunnings[0].runningList);
            return null;
        }
    }

}
