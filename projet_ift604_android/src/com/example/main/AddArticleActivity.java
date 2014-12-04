package com.example.main;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.projet_ift604_android.R;

public class AddArticleActivity extends BaseActivity{
	EditText TextTitreArt;
	EditText TextDescArt;
	Button btnValider;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addarticle);
		InitializeControls();
	}
	
	private void InitializeControls()
	{
		// Get controls 	
		TextTitreArt = (EditText) findViewById(R.id.editTextTitreArt);
		TextDescArt = (EditText) findViewById(R.id.editTextDescArt);
		
		btnValider = (Button) findViewById(R.id.btnValider);
		// Assign a function to them
		btnValider.setOnClickListener(btnValiderListener);
	}
	
	private OnClickListener btnValiderListener = new OnClickListener() {
		
		public void onClick(View v) {

		}
	};
}
