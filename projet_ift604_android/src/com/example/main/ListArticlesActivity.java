package com.example.main;

import java.util.Iterator;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.example.communications.ArticleTransactions;
import com.example.entity.Article;
import com.example.projet_ift604_android.R;
import com.example.utils.Constants;
import com.example.utils.ArticleRowAdapter;
import com.google.gson.Gson;

public class ListArticlesActivity extends BaseActivity implements OnItemClickListener{
	
    ListView articleView;
    Article[] articlesArray = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listarticles);
		
		articleView = (ListView) findViewById(R.id.listArticle);
		articleView.setOnItemClickListener(this);
		
		showArticles();
	}
	
	private void showArticles()
	{
	    ArticleTransactions at = new ArticleTransactions(ListArticlesActivity.this);
        List<Article> articles = at.getallArticles();
        
        if (articles.size() > 0)
        {
            articlesArray = new Article[articles.size()];
            
            if (articles.size() > 0) 
            {
                int position = 0;
                for(Iterator<Article> i = articles.iterator(); i.hasNext(); ) {
                    Article item = i.next();
                    articlesArray[position] = item;
                    position++;
                }
            }
            
            ArticleRowAdapter adapter = new ArticleRowAdapter(ListArticlesActivity.this, 
                    R.layout.article_custom_row, articlesArray);
            
            articleView.setAdapter(adapter);
        }
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
	    if (articlesArray != null)
	    {
	        Article article = articlesArray[position];
	        
	        Intent intent = new Intent(ListArticlesActivity.this, ArticleActivity.class);
	        intent.putExtra(Constants.ARTICLE_EXTRA, new Gson().toJson(article));
	        startActivity(intent);	        
	        ListArticlesActivity.this.finish();
	    }
	}
}
