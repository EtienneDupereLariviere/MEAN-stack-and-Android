package com.example.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.example.communications.HouseTransactions;
import com.example.entity.Maison;
import com.example.projet_ift604_android.R;
import com.example.utils.ConnectionStatus;
import com.example.utils.Constants;
import com.example.utils.Utils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

public class HouseActivity extends BaseActivity {
	
    TextView TextViewAddress;
    TextView textViewUser;
	TextView TextViewCategory;
	TextView TextViewNbrRoom;
	TextView TextViewPrice;
	TextView TextViewCharacteristics;
	TextView TextViewDescription;
	ImageView maison_image;
	Button houseBtnEdit;
	Button houseBtnDelete;
	
	GoogleMap map;
	TextView distanceBetween;
	
	Maison maison;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_house);
		
		initializeControls();
		fillForm();	
	}
	
	private void initializeControls()
	{
		TextViewAddress = (TextView) findViewById(R.id.textViewAddress);
		textViewUser = (TextView) findViewById(R.id.textViewUser);
		TextViewCategory = (TextView) findViewById(R.id.textViewCategory);
		TextViewNbrRoom = (TextView) findViewById(R.id.textViewNbrRoom);
		TextViewPrice = (TextView) findViewById(R.id.textViewPrice);
		TextViewCharacteristics = (TextView) findViewById(R.id.textViewCharacteristics);
		TextViewDescription = (TextView) findViewById(R.id.textViewDescription);
		maison_image = (ImageView) findViewById(R.id.maison_image);
		
		distanceBetween = (TextView) findViewById(R.id.distanceBetween);
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		houseBtnEdit = (Button) findViewById(R.id.houseBtnEdit);
		houseBtnDelete = (Button) findViewById(R.id.houseBtnDelete);
	}
	
	private void fillForm()
	{
	    String json = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            json = extras.getString(Constants.HOUSE_EXTRA);
        
        maison = new Gson().fromJson(json, Maison.class);
        
        if (maison != null) {
        	TextViewAddress.setText(maison.getAddress());
        	textViewUser.setText("Created by : " + maison.getUser().getDisplayName());
        	TextViewCategory.setText(Constants.HOUSE_TYPE + maison.getCategorie().getCategorieName());
    		TextViewNbrRoom.setText(Constants.NUMBER_OF_ROOM + maison.getNbChambre().toString());
    		TextViewPrice.setText(maison.getPrice().toString() + " $");
    		TextViewCharacteristics.setText(Constants.HOUSE_CHARACTERISTICS + maison.getCaracteristic());
    		TextViewDescription.setText(Constants.HOUSE_DESCRIPTION + maison.getDescription());
                     
    		String str = Base64.encodeToString(maison.getImage(), Base64.NO_WRAP);
    	        
    	    String imageWithNoHeader = Utils.removeHeaderImage(str, maison);
    	    Bitmap bmp = Utils.StringToBitMap(imageWithNoHeader);
    	    bmp = Bitmap.createScaledBitmap(bmp, 400, 300, true);
    	    maison_image.setImageBitmap(bmp);
            checkOwner();
            
            // Fill Google Map
            final TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
            tabHost.setup();

            TabSpec spec1=tabHost.newTabSpec("Maison");
            spec1.setContent(R.id.tab1);
            spec1.setIndicator("Maison");

            TabSpec spec2=tabHost.newTabSpec("Carte");
            spec2.setIndicator("Carte");
            spec2.setContent(R.id.tab2);
            
            tabHost.addTab(spec1);
            tabHost.addTab(spec2);
            
            // Load the map before working with it
            tabHost.setOnTabChangedListener(new OnTabChangeListener(){
                public void onTabChanged(String tabId) {
                    if (tabHost.getCurrentTab() == 1) {
                        map.setOnMapLoadedCallback(new OnMapLoadedCallback() {
                            public void onMapLoaded() {
                                distanceBetween.setText(Utils.setBothLocation(map, HouseActivity.this,
                                        new LatLng(maison.getLatitude(), maison.getLongitude())));
                            }
                        });
                    }
                }
            });
        }
	}

	private void checkOwner()
	{
	    if (maison.getUser().get_id().equals(ConnectionStatus.getUserId(HouseActivity.this)))
	    {
	        houseBtnEdit.setOnClickListener(btnEditListener);
	        houseBtnDelete.setOnClickListener(btnDeleteListener);
	        
	        houseBtnEdit.setVisibility(View.VISIBLE);
	        houseBtnDelete.setVisibility(View.VISIBLE);
	    }
	}
	
	private OnClickListener btnEditListener = new OnClickListener() {

        public void onClick(View v) {       
            Intent intent = new Intent(HouseActivity.this, EditHouseActivity.class);
            intent.putExtra(Constants.HOUSE_EXTRA, new Gson().toJson(maison));
            startActivity(intent);          
            HouseActivity.this.finish();
        }
    };
    
    private OnClickListener btnDeleteListener = new OnClickListener() {

        public void onClick(View v) {
            HouseTransactions ht = new HouseTransactions(HouseActivity.this);
            ht.deleteHouse(maison.get_id());
            
            startActivity(Utils.defaultSearchHouses(HouseActivity.this));
            HouseActivity.this.finish();
        }
    };
    
    @Override
    public void onBackPressed() {
        startActivity(Utils.defaultSearchHouses(HouseActivity.this));
        HouseActivity.this.finish();
        
        super.onBackPressed();
    }
}