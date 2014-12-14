package com.example.main;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.example.communications.HouseTransactions;
import com.example.entity.CategorieCollection;
import com.example.entity.Maison;
import com.example.maps.PlacesAutoCompleteAdapter;
import com.example.projet_ift604_android.R;
import com.example.utils.Constants;
import com.example.utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddMaisonActivity extends BaseActivity {

    AutoCompleteTextView TextAdresse;
    
    ImageView clearAddress;
    TextView distanceBetween;
	EditText TextNbrChambre;
	EditText TextPrix;
	EditText TextCarac;
	EditText TextDescArt;
	Spinner SpinnerCategorie;
	Button btnChoisirImage;
	Button btnValider;
	ImageView image;
	Uri imageUri;
	
	List<Marker> markers;
	GoogleMap map;
	LatLng currentPosition;
	ArrayList<Double> housePosition = null;
	PlacesAutoCompleteAdapter paca;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addmaison);
		
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
		                    if (housePosition != null) {
		                        LatLng houseLatLng = new LatLng(housePosition.get(1), housePosition.get(0));

		                        map.clear();
		                        
		                        getCurrentLocation();
		                        
		                        markers.add(map.addMarker(new MarkerOptions()
		                            .position(houseLatLng)
		                            .title("The house is there")
		                            .alpha(0.7f)
		                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))));
		                        
		                        double distance = Utils.CalculationByDistance(currentPosition, houseLatLng);
		                        
		                        distanceBetween.setText("The distance is " + distance + " km");
		                        
		                        Utils.zoomOnMap(markers, map);
		                    }
		                }
		            });
	            }
		    }
		});
		
		InitializeControls();
		Utils.populateSpinner(SpinnerCategorie, AddMaisonActivity.this);
		getCurrentLocation();
	}

	private void InitializeControls() {
	    
	    TextAdresse = (AutoCompleteTextView) findViewById(R.id.editTextAdresse);
	    paca = new PlacesAutoCompleteAdapter(this, R.layout.list_item_maps);
	    TextAdresse.setAdapter(paca);
	    
	    TextAdresse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) 
            {
                String placeId = paca.getListPlacesId().get(position);
                housePosition = paca.getLocation(placeId);
            }
	    });           
	    
		// Get controls
	    clearAddress = (ImageView) findViewById(R.id.redx);
	    distanceBetween = (TextView) findViewById(R.id.distanceBetween);
		TextNbrChambre = (EditText) findViewById(R.id.editTextNbrChambre);
		TextPrix = (EditText) findViewById(R.id.editTextPrix);
		TextCarac = (EditText) findViewById(R.id.editTextCarac);
		TextDescArt = (EditText) findViewById(R.id.editTextDescArt);
		SpinnerCategorie = (Spinner) findViewById(R.id.spinnerCategorie);
		btnValider = (Button) findViewById(R.id.btnValider);
		image = (ImageView) findViewById(R.id.imgHouse_add);
		// Assign a function to them
		btnValider.setOnClickListener(btnValiderListener);
		
		// Initialize markerList
		markers = new ArrayList<Marker>();

		SpinnerCategorie.setOnItemSelectedListener(SpinnerCategorieListener);
		btnChoisirImage = (Button) findViewById(R.id.btnChoisirImage);
		
		// Assign a function to them
		btnChoisirImage.setOnClickListener(btnChoisirImageListener);
		clearAddress.setOnClickListener(imageClearAddressListener);
	}

	private OnClickListener btnValiderListener = new OnClickListener() {
		public void onClick(View v) {
			if (!TextAdresse.getText().toString().equals("")
					&& !TextNbrChambre.getText().toString().equals("")
					&& !TextPrix.getText().toString().equals("")
					&& !TextCarac.getText().toString().equals("")
					&& !TextDescArt.getText().toString().equals("")
					&& image.getDrawable() != null) {

				String categoryName = SpinnerCategorie.getSelectedItem().toString();
				
				if (!categoryName.equals(Constants.ALL_CATEGORIES))
				{
				    Maison newMaison = new Maison();
	                newMaison.setAddress(TextAdresse.getText().toString());
	                newMaison.setNbChambre(Integer.parseInt(TextNbrChambre.getText().toString()));
	                newMaison.setPrice(Integer.parseInt(TextPrix.getText().toString()));
	                newMaison.setCaracteristic(TextCarac.getText().toString());
	                newMaison.setDescription(TextDescArt.getText().toString());
	                newMaison.setCategorie(CategorieCollection.findCategorie(categoryName));
	                
				    if (housePosition != null) {
				        newMaison.setLongitude(housePosition.get(0));
				        newMaison.setLatitude(housePosition.get(1));
				    }
	                
				    byte[] byteImage = null;
				    
				    try {
				        byteImage = Utils.ImageToByte(image);
				        newMaison.setImage(byteImage);
				        Utils.setImageType(AddMaisonActivity.this, imageUri, newMaison);
				    } catch (Exception e) {
                        Log.d("AddMaison", "Problem with ImageToByte " + e.getMessage());
                    }
    				
    				HouseTransactions ht = new HouseTransactions(
    						AddMaisonActivity.this);
    				ht.addHouse(newMaison);
    
    				Intent intent = new Intent(AddMaisonActivity.this,
    						ListMaisonActivity.class);
    				startActivity(intent);
    				AddMaisonActivity.this.finish();
				}
				else
				    Toast.makeText(AddMaisonActivity.this,
	                        getResources().getString(R.string.noCatSelected),
	                        Toast.LENGTH_SHORT).show();
			} else
				Toast.makeText(AddMaisonActivity.this,
						getResources().getString(R.string.notCompleted),
						Toast.LENGTH_SHORT).show();
		}
	};

	private OnItemSelectedListener SpinnerCategorieListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parentView,
				View selectedItemView, int position, long id) {
			// your code here
		}

		public void onNothingSelected(AdapterView<?> parentView) { 
			// your code here
		}
	};

	private OnClickListener btnChoisirImageListener = new OnClickListener() {
		public void onClick(View v) {
		    Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select a profile picture"), Constants.SELECT_PICTURE);
		}
	};
	
	private OnClickListener imageClearAddressListener = new OnClickListener() {
        public void onClick(View v) {
            TextAdresse.setText("");
            if (markers.size() > 1)
                markers.remove(markers.size() - 1);
        }
    };
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.SELECT_PICTURE) {
                imageUri = data.getData();
                image.setImageURI(imageUri);
                image.setVisibility(View.VISIBLE);
            }
        }
    }
	
	private void getCurrentLocation()
    {
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        
        Location loc = Utils.getCurrentLocation(AddMaisonActivity.this);
        
        if (loc == null) {
            // Default position
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(Constants.SHERBROOKE_POSITION, 15));
        }
        else {
            currentPosition = new LatLng(loc.getLatitude(), loc.getLongitude());
            
            markers.add(map.addMarker(new MarkerOptions()
                .position(currentPosition)
                .title("I'm here")
                .alpha(0.7f)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
            
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15));
        }
    }
}
