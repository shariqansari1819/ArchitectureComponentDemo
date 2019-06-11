package com.codebosses.architecturecomponentpractice.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.codebosses.architecturecomponentpractice.R;
import com.codebosses.architecturecomponentpractice.adapter.UserAdapter;
import com.codebosses.architecturecomponentpractice.database.User;
import com.codebosses.architecturecomponentpractice.databinding.ActivityMainBinding;
import com.codebosses.architecturecomponentpractice.endpoints.EndpointKeys;
import com.codebosses.architecturecomponentpractice.lifecycle.MainActivityLifecycle;
import com.codebosses.architecturecomponentpractice.viewmodel.MainActivityViewModel;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements UserAdapter.OnItemLongPressed {

    //    Constant fields....
    private static final int CREATE_USER_CODE = 1;
    private static final int UPDATE_USER_CODE = 2;

    //    Android views fields....
    ActivityMainBinding activityMainBinding;

    //    View model fields....
    MainActivityViewModel mainActivityViewModel;

    //    Adapter fields....
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

//        Setting custom action bar....
        setSupportActionBar((Toolbar) activityMainBinding.appBarMain);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.all_users));
        }

//        Setting life cycle observer with main activity....
        getLifecycle().addObserver(new MainActivityLifecycle());

//        Initialization of view model....
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        userAdapter = new UserAdapter(this);
        userAdapter.setOnItemLongPressed(this);
        activityMainBinding.recyclerViewUsers.setAdapter(userAdapter);
        activityMainBinding.recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));

        mainActivityViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userAdapter.setUserList(users);
            }
        });

//        Setting click handler for data binding....
        ClickHandler clickHandler = new ClickHandler();
        activityMainBinding.setClickHandler(clickHandler);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_USER_CODE && resultCode == RESULT_OK) {
            String id = UUID.randomUUID().toString();
            String firstName = data.getStringExtra(EndpointKeys.FIRST_NAME);
            String lastName = data.getStringExtra(EndpointKeys.LAST_NAME);
            String email = data.getStringExtra(EndpointKeys.EMAIL);
            String password = data.getStringExtra(EndpointKeys.PASSWORD);
            mainActivityViewModel.insertUser(new User(id, firstName, lastName, email, password));
            Toast.makeText(this, "User created successfully.", Toast.LENGTH_SHORT).show();
        } else if (requestCode == UPDATE_USER_CODE && resultCode == RESULT_OK) {
            String id = data.getStringExtra(EndpointKeys.ID);
            String firstName = data.getStringExtra(EndpointKeys.FIRST_NAME);
            String lastName = data.getStringExtra(EndpointKeys.LAST_NAME);
            String email = data.getStringExtra(EndpointKeys.EMAIL);
            String password = data.getStringExtra(EndpointKeys.PASSWORD);
            mainActivityViewModel.updateUser(new User(id, firstName, lastName, email, password));
        } else {
//            Toast.makeText(this, "Could not create user.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLogPressed(View view, User user, int position) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.main_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuUpdate:
                        startUpdateUserActivity(user);
                        return true;
                    case R.id.menuDelete:
                        mainActivityViewModel.deleteUser(user);
                        return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void startUpdateUserActivity(User user) {
        Intent intent = new Intent(this, UpdateUserActivity.class);
        intent.putExtra(EndpointKeys.USER, user);
        startActivityForResult(intent, UPDATE_USER_CODE);
    }

    public class ClickHandler {

        public void onCreateUserClick(View view) {
            Intent intent = new Intent(MainActivity.this, CreateUserActivity.class);
            startActivityForResult(intent, CREATE_USER_CODE);
        }

    }

}