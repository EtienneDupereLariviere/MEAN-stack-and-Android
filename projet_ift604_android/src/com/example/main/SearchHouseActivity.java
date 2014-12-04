package com.example.main;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.projet_ift604_android.R;

public class SearchHouseActivity extends BaseActivity {
	EditText TextPrixMinimum;
	EditText TextPrixMaximum;
	EditText TextVille;
	Spinner SpinnerCat;
	Button btnRechercher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchhouse);
	}

	private void InitializeControls() {
		// Get controls
		TextPrixMinimum = (EditText) findViewById(R.id.editTextPrixMinimum);
		TextPrixMaximum = (EditText) findViewById(R.id.editTextPrixMaximum);
		TextVille = (EditText) findViewById(R.id.editTextVille);
		SpinnerCat = (Spinner) findViewById(R.id.spinnerCat);
		btnRechercher = (Button) findViewById(R.id.btnRechercher);
		// Assign a function to them
		btnRechercher.setOnClickListener(btnRechercherListener);

		SpinnerCat.setOnItemSelectedListener(SpinnerCatListener);

	}

	private OnItemSelectedListener SpinnerCatListener = new OnItemSelectedListener() {
		
		public void onItemSelected(AdapterView<?> parentView,
				View selectedItemView, int position, long id) {
			// your code here
		}

		
		public void onNothingSelected(AdapterView<?> parentView) {
			// your code here
		}
	};
	
	private OnClickListener btnRechercherListener = new OnClickListener() {
		
		public void onClick(View v) {

		}
	};

}
