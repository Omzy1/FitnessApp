package com.example.versioapp1.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WalkingRepository {

    private WalkingDao walkingDao;

    public WalkingRepository(Application application) {
        MyDatabase database = MyDatabase.getDatabase(application);
        walkingDao = database.walkingDao();
    }

    LiveData<List<User>> getUser() {
        return walkingDao.getAllUser();
    }

    LiveData<List<Walking>> getWalking() {
        return walkingDao.getAllWalkingActivityInfo();
    }

    public void insert(UserWithWalking userWithWalking) {
        new insertAsync(walkingDao).execute(userWithWalking);
    }

    public void deleteAll() {
        new insertAsync(walkingDao).execute();
    }

    private static class insertAsync extends AsyncTask<UserWithWalking, Void, Void> {
        private WalkingDao walkingDaoAsyn;

        insertAsync(WalkingDao walkingDao) {
            walkingDaoAsyn = walkingDao;
        }


        @Override
        protected Void doInBackground(UserWithWalking... userWithWalkings) {
            long identifier = walkingDaoAsyn.insertUsers(userWithWalkings[0].user);
            for (Walking walking : userWithWalkings[0].walkingList) {
                walking.setUserId(identifier);
            }
            walkingDaoAsyn.insertWalking(userWithWalkings[0].walkingList);
            return null;
        }
    }

    //deleting users
    private static class deleteAllUsersAsyncTask extends AsyncTask<Void, Void, Void> {
        private WalkingDao walkingDao;

        deleteAllUsersAsyncTask(WalkingDao dao) {
            walkingDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            walkingDao.deleteAll();
            return null;
        }
    }

}

