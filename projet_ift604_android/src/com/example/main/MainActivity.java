package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.example.projet_ift604_android.R;
import com.example.utils.ConnectionStatus;

public class MainActivity extends BaseActivity {
	
    TextView TextViewWelcome;
    Button btnLogOut;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TextViewWelcome = (TextView) findViewById(R.id.textViewWelcome);
		btnLogOut = (Button) findViewById(R.id.btnLogOut);
		btnLogOut.setOnClickListener(btnLogOutListener);
		
		TextViewWelcome.setText("Welcome " + ConnectionStatus.getUsernameSignIn(MainActivity.this) + " , ");
	}
	
	private OnClickListener btnLogOutListener = new OnClickListener() {

        public void onClick(View v) {
            ConnectionStatus.SignOut(MainActivity.this);
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
    };
}
