package com.example.communications;



import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

import com.example.entity.Maison;
import com.example.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class HouseTransactions {

    private String jsonStr;
    private GsonBuilder gsonBuilder;
    private Gson gson;
    private Activity activity;
    
    public HouseTransactions(Activity activity)
    {
        this.jsonStr = "";
        this.gsonBuilder = new GsonBuilder();
        gson = new Gson();
        this.activity = activity;
    }
    
    public List<Maison> getAllHouses(String category, String city, String maxPrice, String minPrice)
    {      
        String url = String.format(Constants.SEARCH_HOUSES, category, city, maxPrice, minPrice);
        jsonStr = new GetRequest(Constants.SERVER_URL + url, null, activity).execute();
        
        if (jsonStr != null && !jsonStr.equals("")) {
            List<Maison> list = new ArrayList<Maison>();
            JSONArray jsonArray;
            try {
                jsonArray = new JSONArray(jsonStr);
            
                int length = jsonArray.length();
                
                for (int i = 0; i < length; i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        return null;
    }
    
    public void deleteHouse(String maisonId)
    {
        String url = Constants.DELETE_EDIT_HOUSE_URL + maisonId;
        jsonStr = new DeleteRequest(Constants.SERVER_URL + url, null, activity).execute();
    }
    
    public void editHouse(Maison maison)
    {
        String url = Constants.DELETE_EDIT_HOUSE_URL + maison.get_id();
        gson = gsonBuilder.registerTypeAdapter(Maison.class, new HouseAdapter.EditHouseAdapter()).create();
        jsonStr = new PutRequest(Constants.SERVER_URL + url, gson.toJson(maison), activity).execute();
    }
    
    public void addHouse(Maison maison)
    {
        String url = Constants.ADD_GET_HOUSES_URL;
        gson = gsonBuilder.registerTypeAdapter(Maison.class, new HouseAdapter.AddHouseAdapter()).create();
        jsonStr = new PostRequest(Constants.SERVER_URL + url, gson.toJson(maison), activity).execute();
    }

}
