package com.example.communications;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetRequest {
    
    private String strUrl;
    
    public GetRequest(String strUrl)
    {
        this.strUrl = strUrl;
    }
    
    // http://localhost:8080/RESTfulExample/json/product/get
    public void execute() {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
     
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
     
            BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));
     
            String output;
            System.out.println("Output from Server .... \n");
            
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
     
            conn.disconnect();
            
        } catch (MalformedURLException e) { 
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
 
      }
    }
}
