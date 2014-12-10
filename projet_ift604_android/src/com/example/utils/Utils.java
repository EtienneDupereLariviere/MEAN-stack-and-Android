package com.example.utils;

import java.io.ByteArrayOutputStream;

import com.example.entity.Maison;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

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
