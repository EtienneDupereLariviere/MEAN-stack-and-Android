package com.example.main;


import com.example.projet_ift604_android.R;

import android.os.Bundle;
import android.widget.TextView;



public class MainActivity extends BaseActivity {
	TextView TextViewWelcome;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextViewWelcome = (TextView) findViewById(R.id.textViewWelcome);
	}



}
