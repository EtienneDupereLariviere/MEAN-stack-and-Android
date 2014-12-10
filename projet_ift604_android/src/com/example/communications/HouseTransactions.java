package com.example.communications;

import java.util.List;
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
    
    public List<Maison> getAllHouses(String categorie, String city, double maxPrice, double minPrice)
    {
        String url = Constants.SEARCH_HOUSES;
        jsonStr = new GetRequest(Constants.SERVER_URL + url, null, activity).execute();
        
        if (jsonStr != null && !jsonStr.equals("")) {
            
        }
        
        return null;
    }

}
