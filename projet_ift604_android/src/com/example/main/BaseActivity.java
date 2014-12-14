package com.example.main;


import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.communications.UserTransactions;
import com.example.projet_ift604_android.R;


public class BaseActivity extends Activity {


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
       
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Intent intent = null;
		switch (item.getItemId()) {
		/*case R.id.menuAccueil:
			intent = new Intent(BaseActivity.this, MainActivity.class);
			break;*/
		case R.id.submenuListArticle:
			intent = new Intent(BaseActivity.this, ListArticlesActivity.class);
			break;
		case R.id.submenuAddArticle:
			intent = new Intent(BaseActivity.this, AddArticleActivity.class);			
			break;
		case R.id.submenuListMaison:
			intent = new Intent(BaseActivity.this, SearchHouseActivity.class);			
			break;
		case R.id.submenuAddMaison:
			intent = new Intent(BaseActivity.this, AddMaisonActivity.class);
			break;
		case R.id.menuLogOut:
		    UserTransactions at = new UserTransactions(BaseActivity.this);
		    at.signOut();
		    intent = new Intent(BaseActivity.this, LoginActivity.class);
		    break;
		}
		if(intent!=null){
			startActivity(intent);
			
			if (item.getItemId() == R.id.menuLogOut)
			    BaseActivity.this.finish();
		}
		
		return super.onOptionsItemSelected(item);
	}
	
}
