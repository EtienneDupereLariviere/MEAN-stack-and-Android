package com.example.main;

import com.example.projet_ift604_android.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ListMaisonActivity extends BaseActivity implements OnItemClickListener {
	ListView maisonView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listmaisons);
	
		maisonView = (ListView) findViewById(R.id.listMaison);
		
		maisonView.setOnItemClickListener(this);
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	/*	MatchDTO matchDTO = listArticle.get(position);
		
		Intent intent = new Intent(MatchsActivity.this, MatchActivity.class);
		intent.putExtra("match", matchDTO.getIdMatch());
		
		startActivity(intent);		*/
	}
}
