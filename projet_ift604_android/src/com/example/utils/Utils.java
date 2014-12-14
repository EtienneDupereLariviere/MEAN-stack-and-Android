package com.example.utils;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.entity.CategorieCollection;
import com.example.entity.Maison;
import com.example.maps.GeolocationManager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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
