package com.example.main;

import com.example.projet_ift604_android.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends Activity {
	EditText prenom;
	EditText nom;
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
		prenom = (EditText) findViewById(R.id.editTextFirstNameUser);
		nom = (EditText) findViewById(R.id.editTextLastNameUser);
		email = (EditText) findViewById(R.id.editTextEmailUser);

		btnSignup = (Button) findViewById(R.id.btnSignup);
		btnSignup.setOnClickListener(btnSignupListener);
	}

	private OnClickListener btnSignupListener = new OnClickListener() {

		public void onClick(View v) {

		}
	};
}
