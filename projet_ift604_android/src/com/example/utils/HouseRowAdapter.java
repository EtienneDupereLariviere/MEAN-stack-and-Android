package com.example.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.entity.Maison;
import com.example.projet_ift604_android.R;

public class HouseRowAdapter extends ArrayAdapter<Maison> {
	private Context context;
	private int layoutResourceId;
	private Maison maisons[] = null;

	public HouseRowAdapter(Context context, int layoutResourceId,
			Maison[] maisons) {
		super(context, layoutResourceId, maisons);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.maisons = maisons;
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        HouseHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new HouseHolder();
            holder.price = (TextView) row.findViewById(R.id.maison_price);
            holder.categorie = (TextView) row.findViewById(R.id.maison_type);
            holder.description = (TextView) row.findViewById(R.id.maison_description);
            holder.image = (ImageView) row.findViewById(R.id.maison_image);
            
            row.setTag(holder);
        }
        else
        {
            holder = (HouseHolder) row.getTag();
        }
        
        if (position % 2 == 0)
            row.setBackgroundResource(R.color.lightGreen);
        else
            row.setBackgroundResource(R.color.lightPink);
        
        Maison maison = maisons[position];
        holder.price.setText(maison.getPrice().toString());
        holder.categorie.setText(maison.getCategorie().getCategorieName());        
        holder.description.setText(maison.getDescription());
       
        String str = Base64.encodeToString(maison.getImage(), Base64.NO_WRAP);
        
        String imageWithNoHeader = removeHeaderImage(str);
        Bitmap bmp = StringToBitMap(imageWithNoHeader);
        holder.image.setImageBitmap(bmp);
        
        return row;
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

	static class HouseHolder {
		TextView categorie;
		TextView description;
		TextView price;
		ImageView image;
	}

}
