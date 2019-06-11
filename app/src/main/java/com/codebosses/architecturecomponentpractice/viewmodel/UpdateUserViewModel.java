package com.codebosses.architecturecomponentpractice.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.codebosses.architecturecomponentpractice.database.DatabaseClient;
import com.codebosses.architecturecomponentpractice.database.User;
import com.codebosses.architecturecomponentpractice.database.UserDao;

public class UpdateUserViewModel extends AndroidViewModel {

    private DatabaseClient databaseClient;
    private UserDao userDao;

    public UpdateUserViewModel(@NonNull Application application) {
        super(application);

        databaseClient = DatabaseClient.getDatabaseClient(application);
        userDao = databaseClient.getAppDatabase().getUserDao();
    }

    public LiveData<User> getUserData(String userId) {
        return userDao.getUserData(userId);
    }
}
