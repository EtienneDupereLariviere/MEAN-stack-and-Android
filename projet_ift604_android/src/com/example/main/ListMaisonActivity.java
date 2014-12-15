package com.example.main;

import java.util.Iterator;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.communications.HouseTransactions;
import com.example.entity.Maison;
import com.example.projet_ift604_android.R;
import com.example.utils.ArticleRowAdapter;
import com.example.utils.Constants;
import com.example.utils.HouseRowAdapter;
import com.google.gson.Gson;

public class ListMaisonActivity extends BaseActivity implements OnItemClickListener {
	ListView maisonView;
	Maison[] maisonsArray = null;
	String category;
	String city;
	String prixmax;
	String prixmin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listmaisons);
		Intent intent = getIntent();
		category = intent.getStringExtra(Constants.CATEGORY_EXTRA);
		city = intent.getStringExtra(Constants.CITY_EXTRA);
		prixmax = intent.getStringExtra(Constants.PRIXMAX_EXTRA);
		prixmin = intent.getStringExtra(Constants.PRIXMIN_EXTRA);
		
		maisonView = (ListView) findViewById(R.id.listMaison);
		
		maisonView.setOnItemClickListener(this);
		
		showHouses();
	}
		
	private void showHouses()
	{
	    HouseTransactions ht = new HouseTransactions(ListMaisonActivity.this);
        List<Maison> maisons = ht.getAllHouses(category, city, prixmax, prixmin);
        
        if (maisons != null && maisons.size() > 0)
        {
            maisonsArray = new Maison[maisons.size()];
            
            if (maisons.size() > 0) 
            {
                int position = 0;
                for(Iterator<Maison> i = maisons.iterator(); i.hasNext(); ) {
                    Maison item = i.next();
                    maisonsArray[position] = item;
                    position++;
                }
            }
            
            HouseRowAdapter adapter = new HouseRowAdapter(ListMaisonActivity.this, 
                    R.layout.maison_custom_row, maisonsArray);
            
            maisonView.setAdapter(adapter);
        }
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
	    if (maisonsArray != null)
	    {
	        Maison maison = maisonsArray[position];
	        
	        Intent intent = new Intent(ListMaisonActivity.this, HouseActivity.class);
	        intent.putExtra(Constants.HOUSE_EXTRA, new Gson().toJson(maison));
	        startActivity(intent);	        
	        ListMaisonActivity.this.finish();
	    }
	}
}
