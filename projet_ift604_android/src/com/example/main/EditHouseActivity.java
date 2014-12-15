package com.example.main;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

public class EditHouseActivity extends BaseActivity {
    
    ImageView clearAddress;
    AutoCompleteTextView editTextAdresse;
    TextView textViewUser;
    Spinner SpinnerCategorie;
    TextView TextViewNbrRoom;
    TextView TextViewPrice;
    TextView TextViewCharacteristics;
    TextView TextViewDescription;
    ImageView maison_image;
    Button btnValider;
    Button btnChoisirImage;
    
    GoogleMap map;
    TextView distanceBetween;
    
    ArrayList<Double> housePosition;
    PlacesAutoCompleteAdapter paca;
    Uri imageUri = null;
    
    Maison maison;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edithouse);
		
		initializeControls();
		fillForm();
	}
	
	private void initializeControls()
	{
	    // Retrieve the house
	    String json = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            json = extras.getString(Constants.HOUSE_EXTRA);
        
        maison = new Gson().fromJson(json, Maison.class);
	    
		// Get controls
        btnChoisirImage = (Button) findViewById(R.id.btnChoisirImage);
        clearAddress = (ImageView) findViewById(R.id.redx);
        editTextAdresse = (AutoCompleteTextView) findViewById(R.id.editTextAdresse);
        textViewUser = (TextView) findViewById(R.id.textViewUser);
        SpinnerCategorie = (Spinner) findViewById(R.id.spinnerCategorie);
        TextViewNbrRoom = (TextView) findViewById(R.id.edittextViewNbrRoom);
        TextViewPrice = (TextView) findViewById(R.id.edittextViewPrice);
        TextViewCharacteristics = (TextView) findViewById(R.id.edittextViewCharacteristics);
        TextViewDescription = (TextView) findViewById(R.id.edittextViewDescription);
        maison_image = (ImageView) findViewById(R.id.maison_image);
        btnValider = (Button) findViewById(R.id.btnValider_edit);
        
        distanceBetween = (TextView) findViewById(R.id.distanceBetween);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        
        // Populate the comboBox
        Utils.populateSpinner(SpinnerCategorie, EditHouseActivity.this);
		
		// Assign a function to them
		btnValider.setOnClickListener(btnValiderListener);
		clearAddress.setOnClickListener(imageClearAddressListener);
		btnChoisirImage.setOnClickListener(btnChoisirImageListener);
		
        paca = new PlacesAutoCompleteAdapter(this, R.layout.list_item_maps);
        editTextAdresse.setAdapter(paca);
		
		editTextAdresse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) 
            {
                String placeId = paca.getListPlacesId().get(position);
                housePosition = paca.getLocation(placeId);
            }
        });
	
		housePosition = new ArrayList<Double>();
		housePosition.add(maison.getLongitude());
		housePosition.add(maison.getLatitude());
	}
	
	private void fillForm()
    {
        String json = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            json = extras.getString(Constants.HOUSE_EXTRA);
        
        maison = new Gson().fromJson(json, Maison.class);
        
        if (maison != null) {
            editTextAdresse.setText(maison.getAddress());
            textViewUser.setText("Created by : " + maison.getUser().getDisplayName());
            TextViewNbrRoom.setText(maison.getNbChambre().toString());
            TextViewPrice.setText(maison.getPrice().toString());
            TextViewCharacteristics.setText(maison.getCaracteristic());
            TextViewDescription.setText(maison.getDescription());
                     
            String str = Base64.encodeToString(maison.getImage(), Base64.NO_WRAP);
                
            String imageWithNoHeader = Utils.removeHeaderImage(str, maison);
            Bitmap bmp = Utils.StringToBitMap(imageWithNoHeader);
            bmp = Bitmap.createScaledBitmap(bmp, 400, 300, true);
            maison_image.setImageBitmap(bmp);
            
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
                                distanceBetween.setText(Utils.setBothLocation(map, EditHouseActivity.this,
                                        new LatLng(housePosition.get(1), housePosition.get(0))));
                            }
                        });
                    }
                }
            });
        }
    }
	
	private OnClickListener btnValiderListener = new OnClickListener() {
		
		public void onClick(View v) {
		    if (!editTextAdresse.getText().toString().equals("")
                    && !TextViewNbrRoom.getText().toString().equals("")
                    && !TextViewPrice.getText().toString().equals("")
                    && !TextViewCharacteristics.getText().toString().equals("")
                    && !TextViewDescription.getText().toString().equals("")
                    && maison_image.getDrawable() != null)
		    {
                String categoryName = SpinnerCategorie.getSelectedItem().toString();
                
                if (!categoryName.equals(Constants.ALL_CATEGORIES)) {
                    Maison newMaison = maison;
                    newMaison.setAddress(editTextAdresse.getText().toString());
                    newMaison.setNbChambre(Integer.parseInt(TextViewNbrRoom.getText().toString()));
                    newMaison.setPrice(Integer.parseInt(TextViewPrice.getText().toString()));
                    newMaison.setCaracteristic(TextViewCharacteristics.getText().toString());
                    newMaison.setDescription(TextViewDescription.getText().toString());
                    newMaison.setCategorie(CategorieCollection.findCategorie(categoryName));
                
                    if (housePosition != null) {
                        newMaison.setLongitude(housePosition.get(0));
                        newMaison.setLatitude(housePosition.get(1));
                    }
                    
                    byte[] byteImage = null;
                    
                    try {
                            byteImage = Utils.ImageToByte(maison_image);
                            newMaison.setImage(byteImage);
                            
                        if (imageUri != null)
                            Utils.setImageType(EditHouseActivity.this, imageUri, newMaison);
                    } catch (Exception e) {
                        Log.d("EditHouse", "Problem with ImageToByte " + e.getMessage());
                    }
                    
                    maison = newMaison;
                    
                    HouseTransactions ht = new HouseTransactions(EditHouseActivity.this);
                    ht.editHouse(newMaison);
                    
                    close();
                }
                else
                    Toast.makeText(EditHouseActivity.this,
                            getResources().getString(R.string.noCatSelected),
                            Toast.LENGTH_SHORT).show();
		    } else
                Toast.makeText(EditHouseActivity.this,
                        getResources().getString(R.string.notCompleted),
                        Toast.LENGTH_SHORT).show();
		}
	};
	
	@Override
    public void onBackPressed() {
	    close();   
        
        super.onBackPressed();
    }
	
	private void close()
	{
	    Intent intent = new Intent(EditHouseActivity.this, HouseActivity.class);
        intent.putExtra(Constants.HOUSE_EXTRA, new Gson().toJson(maison));
        startActivity(intent);
        EditHouseActivity.this.finish();
	}
	
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
            editTextAdresse.setText("");
        }
    };
    
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.SELECT_PICTURE) {
                imageUri = data.getData();
                maison_image.setImageURI(imageUri);
                maison_image.setVisibility(View.VISIBLE);
            }
        }
    }
}
