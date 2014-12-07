package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.communications.ArticleTransactions;
import com.example.entity.Article;
import com.example.projet_ift604_android.R;
import com.example.utils.Constants;
import com.google.gson.Gson;

public class EditArticleActivity extends BaseActivity{
	EditText TextTitreArt;
	EditText TextDescArt;
	Button btnValider;
	Article article;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editarticle);
		
		initializeControls();
	}
	
	private void initializeControls()
	{
	    // Retrieve the article
	    String json = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            json = extras.getString(Constants.ARTICLE_EXTRA);
        
        article = new Gson().fromJson(json, Article.class);
	    
		// Get controls
		TextTitreArt = (EditText) findViewById(R.id.editTextTitreArt_edit);
		TextDescArt = (EditText) findViewById(R.id.editTextDescArt_edit);
		btnValider = (Button) findViewById(R.id.btnValider_edit);
		
		// Assign a function to them
		btnValider.setOnClickListener(btnValiderListener);
		
	    // Fill the form
		TextTitreArt.setText(article.getTitle());
		TextDescArt.setText(article.getContent());
	}
	
	private OnClickListener btnValiderListener = new OnClickListener() {
		
		public void onClick(View v) {
		    if (!TextTitreArt.getText().toString().equals("") &&
		            !TextDescArt.getText().toString().equals(""))
		    {
    		    Article newArticle = article;
    		    newArticle.setTitle(TextTitreArt.getText().toString());
    		    newArticle.setContent(TextDescArt.getText().toString());
    		    
    		    article = newArticle;
    		    
    		    ArticleTransactions at = new ArticleTransactions(EditArticleActivity.this);
                at.editArticle(newArticle);
    
                close();
		    }
		    else
		        Toast.makeText(EditArticleActivity.this, getResources().getString(R.string.noTitleContent), Toast.LENGTH_SHORT).show();
		}
	};
	
	@Override
    public void onBackPressed() {
	    close();   
        
        super.onBackPressed();
    }
	
	private void close()
	{
	    Intent intent = new Intent(EditArticleActivity.this, ArticleActivity.class);
        intent.putExtra(Constants.ARTICLE_EXTRA, new Gson().toJson(article));
        startActivity(intent);
        EditArticleActivity.this.finish();
	}
}
