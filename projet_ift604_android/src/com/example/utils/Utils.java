package com.example.utils;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.entity.CategorieCollection;
import com.example.entity.Maison;
import com.example.main.AddMaisonActivity;
import com.example.main.ListMaisonActivity;
import com.example.main.SearchHouseActivity;
import com.example.maps.GeolocationManager;
import com.example.projet_ift604_android.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Utils {
	
	public static byte[] ImageToByte(ImageView image) {
		Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
		Bitmap resized = Bitmap.createScaledBitmap(bitmap, 176, 131, true);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		resized.compress(Bitmap.CompressFormat.PNG, 100, baos);

		return baos.toByteArray(); 
	}
	
	public static void setImageType(Context context, Uri uriImage, Maison newMaison)
	{
	    String format = GetMimeType(context, uriImage);
	    
	    if (format.contains("png"))
	        newMaison.setImageType(ImageType.Png);
	    else if (format.contains("jpg") || format.contains("jpeg"))
	        newMaison.setImageType(ImageType.Jpeg);
	}
	
	public static Location getCurrentLocation(Context context)
	{
	    GeolocationManager geo = new GeolocationManager(context);
        return geo.getCurrentLocation();
	}
	
	public static double CalculationByDistance(LatLng StartP, LatLng EndP) 
	{
        int Radius=6371; //radius of earth in Km         
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(lon2-lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
        Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult= Radius*c;
        double km=valueResult/1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec =  Integer.valueOf(newFormat.format(km));
        double meter=valueResult%1000;
        int  meterInDec= Integer.valueOf(newFormat.format(meter));
        
        DecimalFormat df = new DecimalFormat("#.##");      
        return Double.valueOf(df.format(Radius * c));
    }
	
	public static void zoomOnMap(List<Marker> markers, GoogleMap googleMap)
	{
	    LatLngBounds.Builder builder = new LatLngBounds.Builder();
	    for (Marker marker : markers) {
	        builder.include(marker.getPosition());
	    }
	    LatLngBounds bounds = builder.build();
	    
	    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 60);
	    googleMap.moveCamera(cu);
	}
	
	public static void populateSpinner(Spinner spinnerCategorie, Activity activity)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                activity, android.R.layout.simple_spinner_item, CategorieCollection.getAllCategoryNames());
        
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategorie.setAdapter(adapter);
    }
	
	public static Intent defaultSearchHouses(Activity activity)
	{
	    Intent intent = new Intent(activity, ListMaisonActivity.class);
        intent.putExtra(Constants.CATEGORY_EXTRA, Constants.QUERY_CATEGORY_AND_CITY);
        intent.putExtra(Constants.CITY_EXTRA, Constants.QUERY_CATEGORY_AND_CITY);
        intent.putExtra(Constants.PRIXMAX_EXTRA, Constants.QUERY_MAX);
        intent.putExtra(Constants.PRIXMIN_EXTRA, Constants.QUERY_MIN);
        
        return intent;
	}
	
	public static LatLng getCurrentLocation(GoogleMap map, Activity activity, List<Marker> markers)
	{
	    if (markers == null)
	        markers = new ArrayList<Marker>();
	    
        Location loc = Utils.getCurrentLocation(activity);
        
        if (loc == null) {
            // Default position
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(Constants.SHERBROOKE_POSITION, 15));
        }
        else {
            LatLng currentPosition = new LatLng(loc.getLatitude(), loc.getLongitude());
            
            markers.add(map.addMarker(new MarkerOptions()
                .position(currentPosition)
                .title("I'm here")
                .alpha(0.7f)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
            
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15));
            
            return currentPosition;
        }
        
        return null;
	}
	
	public static String setBothLocation(GoogleMap map, Activity activity, LatLng housePosition)
	{
	    map.clear();
	    List<Marker> markers = new ArrayList<Marker>();
	    
	    LatLng currentPosition = getCurrentLocation(map, activity, markers);
	    
	    markers.add(map.addMarker(new MarkerOptions()
            .position(housePosition)
            .title("The house is there")
            .alpha(0.7f)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))));
    
        double distance = Utils.CalculationByDistance(currentPosition, housePosition);
        
        Utils.zoomOnMap(markers, map);
        
        return "The distance is " + distance + " km";  
	}
	
	public static String removeHeaderImage(String image, Maison maison) {
        if (image.contains("dataimage/pngbase64")) {
            maison.setImageType(ImageType.Png);
            image = image.substring("dataimage/pngbase64".length(), image.length());
        } else if (image.contains("dataimage/jpegbase64")) {
            maison.setImageType(ImageType.Jpeg);
            image = image.substring("dataimage/jpegbase64".length(), image.length());
        }
        else if (image.contains("dataimage/jpgbase64")){
            maison.setImageType(ImageType.Jpeg);
            image = image.substring("dataimage/jpgbase64".length(), image.length());
        }
        return image;
    }
	
	public static Bitmap StringToBitMap(String encodedString) {
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
	
	private static String GetMimeType(Context context, Uri uriImage)
	{
	    String strMimeType = null;

	    Cursor cursor = context.getContentResolver().query(uriImage,
	                        new String[] { MediaStore.MediaColumns.MIME_TYPE },
	                        null, null, null);

	    if (cursor != null && cursor.moveToNext())
	    {
	        strMimeType = cursor.getString(0);
	    }

	    return strMimeType;
	}
}
