package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.communications.ArticleTransactions;
import com.example.entity.Article;
import com.example.projet_ift604_android.R;
import com.example.utils.Constants;
import com.google.gson.Gson;


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
		    if (!TextTitreArt.getText().toString().equals("") &&
                    !TextDescArt.getText().toString().equals(""))
            {
                Article newArticle = new Article();
                newArticle.setTitle(TextTitreArt.getText().toString());
                newArticle.setContent(TextDescArt.getText().toString());
                
                ArticleTransactions at = new ArticleTransactions(AddArticleActivity.this);
                at.addArticle(newArticle);
    
                Intent intent = new Intent(AddArticleActivity.this, ListArticlesActivity.class);
                startActivity(intent);
                AddArticleActivity.this.finish();
            }
            else 
                Toast.makeText(AddArticleActivity.this, getResources().getString(R.string.noTitleContent), Toast.LENGTH_SHORT).show();
		}
	};
}
