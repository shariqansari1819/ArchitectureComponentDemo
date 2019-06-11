package com.codebosses.architecturecomponentpractice.database;

import android.content.Context;

import androidx.room.Room;

import com.codebosses.architecturecomponentpractice.endpoints.EndpointKeys;

public class DatabaseClient {

    private AppDatabase appDatabase;
    private static volatile DatabaseClient databaseClient;

    private DatabaseClient(Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, EndpointKeys.USER_DATABASE).build();
    }

    public static synchronized DatabaseClient getDatabaseClient(Context context) {
        if (databaseClient == null) {
            synchronized (DatabaseClient.class) {
                if (databaseClient == null)
                    databaseClient = new DatabaseClient(context);
            }
        }
        return databaseClient;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

}