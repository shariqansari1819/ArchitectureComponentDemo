package com.codebosses.architecturecomponentpractice.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.codebosses.architecturecomponentpractice.database.DatabaseClient;
import com.codebosses.architecturecomponentpractice.database.User;
import com.codebosses.architecturecomponentpractice.database.UserDao;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private static final String TAG = MainActivityViewModel.class.getSimpleName();
    private UserDao userDao;
    private DatabaseClient databaseClient;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        databaseClient = DatabaseClient.getDatabaseClient(application);
        userDao = databaseClient.getAppDatabase().getUserDao();
    }

    public LiveData<List<User>> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void insertUser(User user) {
        new InsertUserTask(userDao).execute(user);
    }

    public void updateUser(User user) {
        new UpdateUserTask(userDao).execute(user);
    }

    public void deleteUser(User user) {
        new DeleteUserTask(userDao).execute(user);
    }

    class DeleteUserTask extends AsyncTask<User, Void, Void> {
        UserDao userDao;

        DeleteUserTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.deleteUser(users[0]);
            return null;
        }
    }

    class UpdateUserTask extends AsyncTask<User, Void, Void> {

        UserDao userDao;

        UpdateUserTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.updateUser(users[0]);
            return null;
        }
    }

    class InsertUserTask extends AsyncTask<User, Void, Void> {

        UserDao userDao;

        InsertUserTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insertUser(users[0]);
            return null;
        }

    }

}