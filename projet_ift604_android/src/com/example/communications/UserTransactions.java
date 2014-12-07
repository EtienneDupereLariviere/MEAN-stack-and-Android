package com.example.communications;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.widget.Toast;
import com.example.projet_ift604_android.R;
import com.example.utils.ConnectionStatus;
import com.example.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.User;

public class UserTransactions {
    
    private User user;
    private String jsonStr;
    private GsonBuilder gsonBuilder;
    private Gson gson;
    private Activity activity;
    
    public UserTransactions(User user, Activity activity)
    {
        this.user = user;
        this.jsonStr = "";
        this.gsonBuilder = new GsonBuilder();
        gson = new Gson();
        this.activity = activity;
    }
    
    public UserTransactions(Activity activity)
    {
        this.jsonStr = "";
        this.gsonBuilder = new GsonBuilder();
        gson = new Gson();
        this.activity = activity;
    }
    
    public boolean signIn()
    {
        String url = Constants.SIGN_IN_URL;
        gson = gsonBuilder.registerTypeAdapter(User.class, new UserAdapter.SignInUserAdapter()).create();
        jsonStr = new PostRequest(Constants.SERVER_URL + url, gson.toJson(user), activity).execute();
        
        String userId = "";
        if (jsonStr != null && !jsonStr.equals(""))
        { 
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                userId = jsonObject.getString("_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            User user = gson.fromJson(jsonStr, User.class);
            ConnectionStatus.SignIn(activity, user.getEmail(), user.getUsername(), userId);
            return true;
        }
        else 
        {
            Toast.makeText(activity, activity.getResources().getString(R.string.notGoodUserPass), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    
    public void signOut()
    {
        String url = Constants.SIGN_OUT_URL;
        jsonStr = new GetRequest(Constants.SERVER_URL + url, null, activity).execute();
        
        if (jsonStr != null && !jsonStr.equals(""))
        { 
            ConnectionStatus.SignOut(activity);
        }
    }
}
