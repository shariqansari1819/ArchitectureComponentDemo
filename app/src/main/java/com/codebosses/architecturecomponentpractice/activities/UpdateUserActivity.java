package com.codebosses.architecturecomponentpractice.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.codebosses.architecturecomponentpractice.R;
import com.codebosses.architecturecomponentpractice.database.User;
import com.codebosses.architecturecomponentpractice.databinding.ActivityUpdateUserBinding;
import com.codebosses.architecturecomponentpractice.endpoints.EndpointKeys;
import com.codebosses.architecturecomponentpractice.viewmodel.UpdateUserViewModel;

public class UpdateUserActivity extends AppCompatActivity {

    //    Android views fields....
    private ActivityUpdateUserBinding updateUserBinding;

    //    Instance fields....
    private Bundle extraBundle;
    private String userId;

    //    View model fields....
    UpdateUserViewModel updateUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateUserBinding = DataBindingUtil.setContentView(this, R.layout.activity_update_user);

//        Setting custom action bar....
        setSupportActionBar((Toolbar) updateUserBinding.appBarUpdateUser);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.update_user));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

//        Getting extra data....
        extraBundle = getIntent().getExtras();
        if (extraBundle != null) {
            User user = (User) extraBundle.get(EndpointKeys.USER);
            if (user != null) {
                userId = user.getId();
            }
        }

//        View model initialization....
        updateUserViewModel = ViewModelProviders.of(this).get(UpdateUserViewModel.class);
        updateUserViewModel.getUserData(userId).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                updateUserBinding.editTextFirstName.setText(user.getFirstName());
                updateUserBinding.editTextLastName.setText(user.getLastName());
                updateUserBinding.editTextEmail.setText(user.getEmail());
                updateUserBinding.editTextPassword.setText(user.getPassword());
            }
        });

//        Setting click handler for data binding....
        ClickHandler clickHandler = new ClickHandler();
        updateUserBinding.setClickHandler(clickHandler);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class ClickHandler {

        public void onUpdateClick(View view) {
            Intent intent = new Intent();
            String firstName = updateUserBinding.editTextFirstName.getText().toString();
            String lastName = updateUserBinding.editTextLastName.getText().toString();
            String email = updateUserBinding.editTextEmail.getText().toString();
            String password = updateUserBinding.editTextPassword.getText().toString();
            intent.putExtra(EndpointKeys.ID, userId);
            intent.putExtra(EndpointKeys.FIRST_NAME, firstName);
            intent.putExtra(EndpointKeys.LAST_NAME, lastName);
            intent.putExtra(EndpointKeys.EMAIL, email);
            intent.putExtra(EndpointKeys.PASSWORD, password);
            setResult(RESULT_OK, intent);
            finish();
        }

        public void onCancelClick(View view) {
            finish();
        }

    }
}
