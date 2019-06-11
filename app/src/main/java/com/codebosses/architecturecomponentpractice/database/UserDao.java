package com.codebosses.architecturecomponentpractice.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM user")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM user WHERE id = :userId")
    LiveData<User> getUserData(String userId);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

}
