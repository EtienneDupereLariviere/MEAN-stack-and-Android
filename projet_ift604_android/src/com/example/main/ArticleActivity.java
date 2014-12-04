package com.example.main;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projet_ift604_android.R;

public class ArticleActivity extends BaseActivity {
	TextView TextViewTitre;
	TextView TextViewDateUser;
	TextView TextViewDesc;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article);
		
		TextViewTitre = (TextView) findViewById(R.id.textViewTitre);
		TextViewDateUser = (TextView) findViewById(R.id.textViewDateUser);
		TextViewDesc = (TextView) findViewById(R.id.textViewDesc);
	}
}
