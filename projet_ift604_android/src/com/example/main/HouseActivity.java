package com.example.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.communications.ArticleTransactions;
import com.example.entity.Article;
import com.example.entity.Maison;
import com.example.projet_ift604_android.R;
import com.example.utils.ConnectionStatus;
import com.example.utils.Constants;
import com.google.gson.Gson;

public class HouseActivity extends BaseActivity {
	
    TextView TextViewAddress;
	TextView TextViewCategory;
	TextView TextViewNbrRoom;
	TextView TextViewPrice;
	TextView TextViewCharacteristics;
	TextView TextViewDescription;
	ImageView maison_image;
	Button houseBtnEdit;
	Button houseBtnDelete;
	
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
		TextViewCategory = (TextView) findViewById(R.id.textViewCategory);
		TextViewNbrRoom = (TextView) findViewById(R.id.textViewNbrRoom);
		TextViewPrice = (TextView) findViewById(R.id.textViewPrice);
		TextViewCharacteristics = (TextView) findViewById(R.id.textViewCharacteristics);
		TextViewDescription = (TextView) findViewById(R.id.textViewDescription);
		
		houseBtnEdit = (Button) findViewById(R.id.houseBtnEdit);
		houseBtnDelete = (Button) findViewById(R.id.houseBtnEdit);
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
        	TextViewCategory.setText(maison.getCategorie().getCategorieName());
    		TextViewNbrRoom.setText(maison.getNbChambre().toString());
    		TextViewPrice.setText(maison.getPrice().toString());
    		TextViewCharacteristics.setText(maison.getCaracteristic());
    		TextViewDescription.setText(maison.getDescription());
                     
    		 //String str = Base64.encodeToString(maison.getImage(), Base64.NO_WRAP);
    	        
    	    // String imageWithNoHeader = removeHeaderImage(str);
    	    // Bitmap bmp = StringToBitMap(imageWithNoHeader);
    	   //  maison_image.setImageBitmap(bmp);
            //checkOwner();
        }
	}
	
	
	private String removeHeaderImage(String image) {
		if (image.contains("dataimage/pngbase64")) {
			image = image.substring("dataimage/pngbase64".length(), image.length());
		} else if (image.contains("dataimage/jpegbase64")) {
			image = image.substring("dataimage/jpegbase64".length(), image.length());
		}
		else if (image.contains("dataimage/jpgbase64")){
			image = image.substring("dataimage/jpgbase64".length(), image.length());
		}
		return image;
	}

	public Bitmap StringToBitMap(String encodedString) {
		try {
			byte[] encodeByte = Base64.decode(encodedString, Base64.NO_WRAP);
			Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
					encodeByte.length);
			return bitmap;
		} catch (Exception e) {
			e.getMessage();
			return null;
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
            Intent intent = new Intent(HouseActivity.this, EditArticleActivity.class);
            intent.putExtra(Constants.HOUSE_EXTRA, new Gson().toJson(maison));
            startActivity(intent);          
            HouseActivity.this.finish();
        }
    };
    
    private OnClickListener btnDeleteListener = new OnClickListener() {

        public void onClick(View v) {
            ArticleTransactions at = new ArticleTransactions(HouseActivity.this);
            at.deleteArticle(maison.get_id());
            
            Intent intent = new Intent(HouseActivity.this, ListArticlesActivity.class);
            startActivity(intent);
            HouseActivity.this.finish();
        }
    };
    
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HouseActivity.this, ListArticlesActivity.class);
        startActivity(intent);
        HouseActivity.this.finish();
        
        super.onBackPressed();
    }
}