package com.example.main;

import com.example.projet_ift604_android.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

	EditText userName;
	EditText password;
	Button btnLogin;
	Button btnSignup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		userName = (EditText) findViewById(R.id.editTextUsername);
		password = (EditText) findViewById(R.id.editTextPassword);

		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(btnLoginListener);

		btnSignup = (Button) findViewById(R.id.btnLoginSignup);
		btnSignup.setOnClickListener(btnLoginSignupListener);
	}

	private OnClickListener btnLoginListener = new OnClickListener() {

		public void onClick(View v) {	
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent);
		}
	};

	private OnClickListener btnLoginSignupListener = new OnClickListener() {

		public void onClick(View v) {
			Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
			startActivity(intent);
		}
	};
}
