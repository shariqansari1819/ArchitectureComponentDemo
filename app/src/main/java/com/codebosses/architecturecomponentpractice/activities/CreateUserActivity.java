package com.codebosses.architecturecomponentpractice.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.codebosses.architecturecomponentpractice.R;
import com.codebosses.architecturecomponentpractice.databinding.ActivityCreateUserBinding;
import com.codebosses.architecturecomponentpractice.endpoints.EndpointKeys;

public class CreateUserActivity extends AppCompatActivity {

    //    Android views fields....
    private ActivityCreateUserBinding createUserBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createUserBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_user);

//        Setting custom action bar....
        setSupportActionBar((Toolbar) createUserBinding.appBarCreateUser);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.create_new_user));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

//        Setting click handler for data binding....
        ClickHandler clickHandler = new ClickHandler();
        createUserBinding.setClickHandler(clickHandler);

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

        public void onCreateClick(View view) {
            String firstName = createUserBinding.editTextFirstName.getText().toString();
            String lastName = createUserBinding.editTextLastName.getText().toString();
            String emailAddress = createUserBinding.editTextEmail.getText().toString();
            String password = createUserBinding.editTextPassword.getText().toString();
            Intent intent = new Intent();
            if (!TextUtils.isEmpty(firstName) &&
                    !TextUtils.isEmpty(lastName) &&
                    !TextUtils.isEmpty(emailAddress) &&
                    !TextUtils.isEmpty(password)) {
                intent.putExtra(EndpointKeys.FIRST_NAME, firstName);
                intent.putExtra(EndpointKeys.LAST_NAME, lastName);
                intent.putExtra(EndpointKeys.EMAIL, emailAddress);
                intent.putExtra(EndpointKeys.PASSWORD, password);
                setResult(RESULT_OK, intent);
            } else {
                setResult(RESULT_CANCELED, intent);
            }
            finish();
        }

        public void onCancelClick(View view) {
            finish();
        }

    }
}