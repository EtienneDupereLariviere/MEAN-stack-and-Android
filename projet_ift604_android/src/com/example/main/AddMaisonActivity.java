package com.example.main;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.projet_ift604_android.R;

public class AddMaisonActivity extends BaseActivity {
	
	EditText TextAdresse;
	EditText TextNbrChambre;
	EditText TextPrix;
	EditText TextCarac;
	EditText TextDescArt;
	Spinner SpinnerCategorie;
	Button btnChoisirImage;
	Button btnValider;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addmaison);
		InitializeControls();
	}
	
	private void InitializeControls()
	{
		// Get controls 
		TextAdresse = (EditText) findViewById(R.id.editTextAdresse);
		TextNbrChambre = (EditText) findViewById(R.id.editTextNbrChambre);
		TextPrix = (EditText) findViewById(R.id.editTextPrix);
		TextCarac = (EditText) findViewById(R.id.editTextCarac);
		TextDescArt = (EditText) findViewById(R.id.editTextDescArt);
		SpinnerCategorie = (Spinner) findViewById(R.id.spinnerCategorie);
		btnValider = (Button) findViewById(R.id.btnValider);
		// Assign a function to them
		btnValider.setOnClickListener(btnValiderListener);
		
		SpinnerCategorie.setOnItemSelectedListener(SpinnerCategorieListener);
		
		btnChoisirImage = (Button) findViewById(R.id.btnChoisirImage);
		// Assign a function to them
		btnChoisirImage.setOnClickListener(btnChoisirImageListener);
	}
	
	private OnClickListener btnValiderListener = new OnClickListener() {
		public void onClick(View v) {

		}
	};
	
	private OnItemSelectedListener SpinnerCategorieListener = new OnItemSelectedListener() {
	
		public void onItemSelected(AdapterView<?> parentView,
				View selectedItemView, int position, long id) {
			// your code here
		}

	
		public void onNothingSelected(AdapterView<?> parentView) {
			// your code here
		}
	};
	
	private OnClickListener btnChoisirImageListener = new OnClickListener() {
		
		public void onClick(View v) {

		}
	};
}
