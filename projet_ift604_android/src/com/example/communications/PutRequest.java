package com.example.communications;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

import com.example.utils.Constants;

public class PutRequest {

    private String strUrl;
    private int htmlCode;
    private String json;
    
    public PutRequest(String strUrl, String json)
    {
        this.strUrl = strUrl;
        this.htmlCode = 0;
        this.json = json;
    }
    
    public void execute() {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
     
            String input = "{\"title\":\"Test\",\"content\":\"Yahoo j'ai gagne\"}";
            
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();
            
            htmlCode = conn.getResponseCode();
     
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
     
            String output;          
            while ((output = br.readLine()) != null) {
                Log.i(Constants.PUT_REQUEST, output);
            }
     
            conn.disconnect();
            
        } catch (MalformedURLException e) { 
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
