package com.example.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.communications.HouseTransactions;
import com.example.entity.CategorieCollection;
import com.example.entity.Maison;
import com.example.projet_ift604_android.R;
import com.example.utils.Constants;
import com.example.utils.Utils;

public class AddMaisonActivity extends BaseActivity {

	EditText TextAdresse;
	EditText TextNbrChambre;
	EditText TextPrix;
	EditText TextCarac;
	EditText TextDescArt;
	Spinner SpinnerCategorie;
	Button btnChoisirImage;
	Button btnValider;
	ImageView image;
	Uri imageUri;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addmaison);
		InitializeControls();
		populateSpinner();
	}
	
	// TODO: Make generic function
	private void populateSpinner()
    {
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, CategorieCollection.getAllCategoryNames());
        
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCategorie.setAdapter(adapter);
    }

	private void InitializeControls() {
		// Get controls
		TextAdresse = (EditText) findViewById(R.id.editTextAdresse);
		TextNbrChambre = (EditText) findViewById(R.id.editTextNbrChambre);
		TextPrix = (EditText) findViewById(R.id.editTextPrix);
		TextCarac = (EditText) findViewById(R.id.editTextCarac);
		TextDescArt = (EditText) findViewById(R.id.editTextDescArt);
		SpinnerCategorie = (Spinner) findViewById(R.id.spinnerCategorie);
		btnValider = (Button) findViewById(R.id.btnValider);
		image = (ImageView) findViewById(R.id.imgHouse_add);
		// Assign a function to them
		btnValider.setOnClickListener(btnValiderListener);

		SpinnerCategorie.setOnItemSelectedListener(SpinnerCategorieListener);

		btnChoisirImage = (Button) findViewById(R.id.btnChoisirImage);
		// Assign a function to them
		btnChoisirImage.setOnClickListener(btnChoisirImageListener);
	}

	private OnClickListener btnValiderListener = new OnClickListener() {
		public void onClick(View v) {
			if (!TextAdresse.getText().toString().equals("")
					&& !TextNbrChambre.getText().toString().equals("")
					&& !TextPrix.getText().toString().equals("")
					&& !TextCarac.getText().toString().equals("")
					&& !TextDescArt.getText().toString().equals("")
					&& image.getDrawable() != null) {
				Maison newMaison = new Maison();
				newMaison.setAddress(TextAdresse.getText().toString());
				newMaison.setNbChambre(Integer.parseInt(TextNbrChambre.getText().toString()));
				newMaison.setPrice(Integer.parseInt(TextPrix.getText().toString()));
				newMaison.setCaracteristic(TextCarac.getText().toString());
				newMaison.setDescription(TextDescArt.getText().toString());
				
				String categoryName = SpinnerCategorie.getSelectedItem().toString();
				
				if (!categoryName.equals(Constants.ALL_CATEGORIES))
				{
				    byte[] byteImage = null;
				    
				    try {
				        byteImage = Utils.ImageToByte(image);
				        newMaison.setImage(byteImage);
				        Utils.setImageType(AddMaisonActivity.this, imageUri, newMaison);
				    } catch (Exception e) {
                        Log.d("AddMaison", "Problem with ImageToByte " + e.getMessage());
                    }
				    
    	            newMaison.setCategorie(CategorieCollection.findCategorie(categoryName));
    				
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
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.SELECT_PICTURE) {
                imageUri = data.getData();
                image.setImageURI(imageUri);
            }
        }
    }
}
