package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.projet_ift604_android.R;

public class ListArticlesActivity extends BaseActivity implements OnItemClickListener{
	ListView articleView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listarticles);
		
		articleView = (ListView) findViewById(R.id.listArticle);
		
		articleView.setOnItemClickListener(this);
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	/*	MatchDTO matchDTO = listArticle.get(position);
		
		Intent intent = new Intent(MatchsActivity.this, MatchActivity.class);
		intent.putExtra("match", matchDTO.getIdMatch());
		
		startActivity(intent);		*/
	}
	
}
