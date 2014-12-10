package com.example.communications;

import java.lang.reflect.Type;
import android.util.Base64;
import com.example.entity.Maison;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class HouseAdapter {

	public static class EditHouseAdapter implements JsonSerializer<Maison> {
		public JsonElement serialize(Maison maison, Type type,
				JsonSerializationContext jsc) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("_id", maison.get_id());
			jsonObject.addProperty("address", maison.getAddress());
			jsonObject.addProperty("category", maison.getCategorie().getCategorieName());
			jsonObject.addProperty("characteristics", maison.getCaracteristic());
			jsonObject.addProperty("description", maison.getDescription());
			jsonObject.addProperty("image", maison.getImageType() + Base64.encodeToString(maison.getImage(), Base64.NO_WRAP));
			jsonObject.addProperty("nbRoom", maison.getNbChambre());
			jsonObject.addProperty("price", maison.getPrice());

			return jsonObject;
		}
	}


	public static class AddHouseAdapter implements JsonSerializer<Maison> {
		public JsonElement serialize(Maison maison, Type type,
				JsonSerializationContext jsc) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("address", maison.getAddress());
			jsonObject.addProperty("category", maison.getCategorie().get_id());
			jsonObject.addProperty("characteristics", maison.getCaracteristic());
			jsonObject.addProperty("description", maison.getDescription());
			jsonObject.addProperty("image", maison.getImageType() + Base64.encodeToString(maison.getImage(), Base64.NO_WRAP));
			jsonObject.addProperty("nbRoom", maison.getNbChambre());
			jsonObject.addProperty("price", maison.getPrice());
			
			// TODO: Change that
			jsonObject.addProperty("latitude", 45.4034341);
			jsonObject.addProperty("longitude", -71.89141760000001);

			return jsonObject;
		}
	}
}
