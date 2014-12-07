package com.example.communications;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.example.utils.ConnectionStatus;
import com.example.utils.Constants;
import android.app.Activity;
import android.util.Log;

public class PostRequest {
    
    private String strUrl;
    private int htmlCode;
    private String json;
    private Activity activity;
    
    public PostRequest(String strUrl, String json, Activity activity)
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
            
            conn.setDoOutput(true);
            
            // Getting cookie if connected
            if (ConnectionStatus.IsSignedIn(activity))
                conn.setRequestProperty(Constants.COOKIE, ConnectionStatus.getCookie(activity));
            
            conn.setRequestMethod("POST"); 
            conn.setRequestProperty("Content-Type", "application/json");
     
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            
            htmlCode = conn.getResponseCode();
            String result = "";
            
            if (htmlCode == 200)
            {
                // Saving cookie if not connected
                if (!ConnectionStatus.IsSignedIn(activity))
                    ConnectionStatus.SetCookie(activity, conn.getHeaderField(Constants.COOKIES_HEADER));
         
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));
         
                String output;      
                while ((output = br.readLine()) != null) {
                    result += output;
                    Log.i(Constants.POST_REQUEST, output);
                }
            }
     
            conn.disconnect();
            
            return result;
            
        } catch (MalformedURLException e) { 
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
