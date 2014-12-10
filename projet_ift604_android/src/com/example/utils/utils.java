package com.example.utils;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

public class utils {

	
	public static byte[] ImageToByte(ImageView image) {
		Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
		Bitmap resized = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		resized.compress(Bitmap.CompressFormat.PNG, 100, baos);

		return baos.toByteArray();
	}
}
