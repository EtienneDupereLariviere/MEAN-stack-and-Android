package com.example.communications;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.utils.ConnectionStatus;
import com.example.utils.Constants;

import android.app.Activity;
import android.util.Log;

public class GetRequest {
    
    private String strUrl;
    private int htmlCode;
    private String json;
    private Activity activity;
    
    public GetRequest(String strUrl, String json, Activity activity)
    {
        this.strUrl = strUrl;
        this.htmlCode = 0;
        this.json = json;
        this.activity = activity;
    }
    
    public String execute() {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            conn.setRequestProperty(Constants.COOKIE, ConnectionStatus.getCookie(activity));
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
     
            this.htmlCode = conn.getResponseCode();
            String result = "";
     
            BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));
     
            String output;           
            while ((output = br.readLine()) != null) {
                result += output;
                Log.i(Constants.GET_REQUEST, output);
            }
     
            conn.disconnect();
            
            return result;
            
        } catch (MalformedURLException e) { 
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return json;
    }
}
