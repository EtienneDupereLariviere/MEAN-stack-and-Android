package com.example.maps;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

public class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
    
    private static final String LOG_TAG = "PlacesAutoComplete";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String PLACES_DETAILS = "/details";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyCzxhsd5WzS2gocLMO6qmSkoaFUZcjyaOE";
    
    private ArrayList<String> resultList;
    private StringBuilder jsonResults;
    
    private ArrayList<String> listPlacesId;

    public PlacesAutoCompleteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public String getItem(int index) {
        return resultList.get(index);
    }
    
    public ArrayList<String> getListPlacesId()
    {
        return listPlacesId;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    // Retrieve the autocomplete results.
                    resultList = autocomplete(constraint.toString());

                    // Assign the data to the FilterResults
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }};
        return filter;
    }
    
    public ArrayList<Double> getLocation(String placeId)
    {
        ArrayList<Double> result = new ArrayList<Double>(2);
        HttpURLConnection conn = null;
        jsonResults = new StringBuilder();
        
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + PLACES_DETAILS + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&placeid=" + placeId);

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
            
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return null;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            } 
        }
        
        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            
            JSONObject resultNode = jsonObj.getJSONObject("result");
            JSONObject geometry = resultNode.getJSONObject("geometry");
            JSONObject location = geometry.getJSONObject("location");
            
            result.add(location.getDouble("lng"));
            result.add(location.getDouble("lat"));
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }
        
        return result;
    }
    
    private ArrayList<String> autocomplete(String input) {
        ArrayList<String> resultList = null;

        HttpURLConnection conn = null;
        jsonResults = new StringBuilder();
        
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&components=country:ca");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
            
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            } 
        }
        
        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(predsJsonArray.length());
            listPlacesId = new ArrayList<String>(predsJsonArray.length());
            
            for (int i = 0; i < predsJsonArray.length(); i++) {
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
                listPlacesId.add(predsJsonArray.getJSONObject(i).getString("place_id"));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }
}
