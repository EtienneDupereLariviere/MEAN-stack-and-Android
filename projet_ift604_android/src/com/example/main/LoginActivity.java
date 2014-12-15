package com.example.main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.communications.UserTransactions;
import com.example.entity.User;
import com.example.projet_ift604_android.R;
import com.example.utils.ConnectionStatus;

public class LoginActivity extends Activity {

	EditText userName;
	EditText password;
	Button btnLogin;
	Button btnSignup;
	TextView textServerOffline;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		// TODO: Change request with AsychTask
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		
		initializeControls();
		
		// Check if the connection is available
		Socket socket = new Socket();
        boolean reachable = false;
        
        try {
            SocketAddress sockaddr = new InetSocketAddress("192.168.43.110", 3000);
            socket.connect(sockaddr, 4000);
            reachable = true;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {            
            if (socket != null) try { socket.close(); } catch(IOException e) {}
        }
        
        if (reachable) {
            if (ConnectionStatus.IsSignedIn(LoginActivity.this))
                logIn();
        }
        else {
            textServerOffline.setVisibility(View.VISIBLE);
            btnLogin.setEnabled(false);
            btnSignup.setEnabled(false);
        }
	}

	private OnClickListener btnLoginListener = new OnClickListener() {

		public void onClick(View v) {
		    
		    if (!userName.getText().toString().equals("") && !password.getText().toString().equals(""))
		    {
    		    User user = new User(userName.getText().toString(), password.getText().toString());
    		    UserTransactions ut = new UserTransactions(user, LoginActivity.this);
    		    
    		    if (ut.signIn()) {
    		        logIn();
    		    }
		    }
		    else
		        Toast.makeText(LoginActivity.this, getResources().getString(R.string.noUserPass), Toast.LENGTH_SHORT).show();
		}
	};
	
	private void logIn()
	{
	    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
	}

	private OnClickListener btnLoginSignupListener = new OnClickListener() {

		public void onClick(View v) {
			Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
			startActivity(intent);
			LoginActivity.this.finish();
		}
	};
	
	private void initializeControls()
	{
	    userName = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(btnLoginListener);

        btnSignup = (Button) findViewById(R.id.btnLoginSignup);
        btnSignup.setOnClickListener(btnLoginSignupListener);
        
        textServerOffline = (TextView) findViewById(R.id.textServerOffline);
	}
}
