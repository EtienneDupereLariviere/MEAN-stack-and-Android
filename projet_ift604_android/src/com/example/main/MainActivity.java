package com.example.main;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.entity.Categorie;
import com.example.projet_ift604_android.R;
import com.example.utils.ConnectionStatus;

public class MainActivity extends BaseActivity {
	
    TextView TextViewWelcome;
    Button btnLogOut;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TextViewWelcome = (TextView) findViewById(R.id.textViewWelcome);
		btnLogOut = (Button) findViewById(R.id.btnLogOut);
		btnLogOut.setOnClickListener(btnLogOutListener);
		
		TextViewWelcome.setText("Welcome " + ConnectionStatus.getUsernameSignIn(MainActivity.this) + " , ");
		
		initializeCategories();
		
		//TODO: check if online
	}
	
	private void initializeCategories() 
	{	    
	    new Categorie("All categories", null, null);
	    new Categorie("Apartment", "A suite of rooms forming one residence, typically in a building containing a number of these.", "547298b6a49250c04f0c30a5");
	    new Categorie("Chalet - cottage", "A wooden house or cottage with overhanging eaves, typically found in the Swiss Alps.", "54729961a49250c04f0c30a6");
	    new Categorie("Bungalow", "A low house, with a broad front porch, having either no upper floor or upper rooms set in the roof, typically with dormer windows.", "547299baa49250c04f0c30a7");
	    new Categorie("Two-Storey house", "House", "54729a59a49250c04f0c30a9");
	    new Categorie("Split-level", "A split-level building.", "54729a97a49250c04f0c30aa");
	    new Categorie("One-and-a-half-storey", "Storey", "54729b03a49250c04f0c30ab");
	}
	
	private OnClickListener btnLogOutListener = new OnClickListener() {

        public void onClick(View v) {
            ConnectionStatus.SignOut(MainActivity.this);
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
    };
}
