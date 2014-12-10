package com.example.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.example.communications.UserTransactions;
import com.example.entity.User;
import com.example.projet_ift604_android.R;

public class SignupActivity extends Activity {
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText userName;
    EditText password;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        userName = (EditText) findViewById(R.id.editTextUsernameUser);
        password = (EditText) findViewById(R.id.editTextPasswordUser);
        firstName = (EditText) findViewById(R.id.editTextFirstNameUser);
        lastName = (EditText) findViewById(R.id.editTextLastNameUser);
        email = (EditText) findViewById(R.id.editTextEmailUser);

        btnSignup = (Button) findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(btnSignupListener);
    }

    private OnClickListener btnSignupListener = new OnClickListener() {

        public void onClick(View v) {
            User newUser = new User();
            newUser.setDisplayName(userName.getText().toString());
            newUser.setEmail(email.getText().toString());
            newUser.setFirstName(firstName.getText().toString());
            newUser.setLastName(lastName.getText().toString());
            newUser.setUsername(userName.getText().toString());
            newUser.setPassword(password.getText().toString());
            
            UserTransactions us = new UserTransactions(SignupActivity.this);
            us.addUser(newUser);

            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);

            SignupActivity.this.finish();
            
        }
    };
}
