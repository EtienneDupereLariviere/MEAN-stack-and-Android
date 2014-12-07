package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.example.communications.ArticleTransactions;
import com.example.projet_ift604_android.R;
import com.example.utils.ConnectionStatus;
import com.example.utils.Constants;
import com.google.gson.Gson;
import entity.Article;

public class ArticleActivity extends BaseActivity {
	
    TextView TextViewTitre;
	TextView TextViewDateUser;
	TextView TextViewDesc;
	Button articleBtnEdit;
	Button articleBtnDelete;
	
	Article article;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article);
		
		initializeControls();
		fillForm();	
	}
	
	private void initializeControls()
	{
	    TextViewTitre = (TextView) findViewById(R.id.textViewTitre);
		TextViewDateUser = (TextView) findViewById(R.id.textViewDateUser);
		TextViewDesc = (TextView) findViewById(R.id.textViewDesc);
		
		articleBtnEdit = (Button) findViewById(R.id.articleBtnEdit);
		articleBtnDelete = (Button) findViewById(R.id.articleBtnDelete);
	}
	
	private void fillForm()
	{
	    String json = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            json = extras.getString(Constants.ARTICLE_EXTRA);
        
        article = new Gson().fromJson(json, Article.class);
        
        if (article != null) {
            TextViewTitre.setText(article.getTitle());
            TextViewDateUser.setText("Posted on " + article.getCreated() + " by " + article.getUser().getDisplayName());
            TextViewDesc.setText(article.getContent());
            
            checkOwner();
        }
	}
	
	private void checkOwner()
	{
	    if (article.getUser().get_id().equals(ConnectionStatus.getUserId(ArticleActivity.this)))
	    {
	        articleBtnEdit.setOnClickListener(btnEditListener);
	        articleBtnDelete.setOnClickListener(btnDeleteListener);
	        
	        articleBtnEdit.setVisibility(View.VISIBLE);
	        articleBtnDelete.setVisibility(View.VISIBLE);
	    }
	}
	
	private OnClickListener btnEditListener = new OnClickListener() {

        public void onClick(View v) {       
            Intent intent = new Intent(ArticleActivity.this, EditArticleActivity.class);
            intent.putExtra(Constants.ARTICLE_EXTRA, new Gson().toJson(article));
            startActivity(intent);          
            ArticleActivity.this.finish();
        }
    };
    
    private OnClickListener btnDeleteListener = new OnClickListener() {

        public void onClick(View v) {
            ArticleTransactions at = new ArticleTransactions(ArticleActivity.this);
            at.deleteArticle(article.get_id());
            
            Intent intent = new Intent(ArticleActivity.this, ListArticlesActivity.class);
            startActivity(intent);
            ArticleActivity.this.finish();
        }
    };
    
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ArticleActivity.this, ListArticlesActivity.class);
        startActivity(intent);
        ArticleActivity.this.finish();
        
        super.onBackPressed();
    }
}
