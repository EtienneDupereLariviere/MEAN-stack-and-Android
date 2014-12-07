package com.example.main;

import java.util.Iterator;
import java.util.List;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.example.communications.ArticleTransactions;
import com.example.projet_ift604_android.R;
import com.example.utils.CustomRowAdapter;

import entity.Article;

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
            
            CustomRowAdapter adapter = new CustomRowAdapter(ListArticlesActivity.this, 
                    R.layout.custom_row, articlesArray);
            
            articleView.setAdapter(adapter);
        }
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
	    if (articlesArray != null)
	    {
	        String articleId = articlesArray[position].get_id();
	        Toast.makeText(ListArticlesActivity.this, articleId, Toast.LENGTH_SHORT).show();
	    }
	}
	
}
