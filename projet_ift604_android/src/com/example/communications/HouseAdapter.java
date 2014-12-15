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
			jsonObject.addProperty("category", maison.getCategorie().get_id());
			jsonObject.addProperty("characteristics", maison.getCaracteristic());
			jsonObject.addProperty("description", maison.getDescription());
			jsonObject.addProperty("image", maison.getImageType() + Base64.encodeToString(maison.getImage(), Base64.NO_WRAP));
			jsonObject.addProperty("nbRoom", maison.getNbChambre());
			jsonObject.addProperty("price", maison.getPrice());
			jsonObject.addProperty("latitude", maison.getLatitude());
            jsonObject.addProperty("longitude", maison.getLongitude());
            
            JsonObject user = new JsonObject();
            user.addProperty("_id", maison.getUser().get_id());
            user.addProperty("displayName", maison.getUser().getDisplayName());
            
            jsonObject.add("user", user);

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
			jsonObject.addProperty("latitude", maison.getLatitude());
			jsonObject.addProperty("longitude", maison.getLongitude());

			return jsonObject;
		}
	}
}
