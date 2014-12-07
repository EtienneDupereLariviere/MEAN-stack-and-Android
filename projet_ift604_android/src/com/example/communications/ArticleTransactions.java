package com.example.communications;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;

import com.example.entity.Article;
import com.example.entity.User;
import com.example.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ArticleTransactions {

    private String jsonStr;
    private GsonBuilder gsonBuilder;
    private Gson gson;
    private Activity activity;
    
    public ArticleTransactions(Activity activity)
    {
        this.jsonStr = "";
        this.gsonBuilder = new GsonBuilder();
        gson = new Gson();
        this.activity = activity;
    }
    
    public List<Article> getallArticles()
    {
        String url = Constants.ADD_GET_ARTICLES_URL;
        jsonStr = new GetRequest(Constants.SERVER_URL + url, null, activity).execute();
        
        if (jsonStr != null && !jsonStr.equals("")) {
            
            try {
                JSONArray jsonArray = new JSONArray(jsonStr);
                
                int length = jsonArray.length();
                
                String userId;
                String userDisplayName;
                String articleId;
                String content;
                String title;
                String created;
                
                List<Article> list = new ArrayList<Article>();
                
                for (int i = 0; i < length; i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    JSONObject userField = obj.getJSONObject("user");

                    userId = userField.getString("_id");
                    userDisplayName = userField.getString("displayName");
                    
                    articleId = obj.getString("_id");
                    content = obj.getString("content");
                    title = obj.getString("title");
                    created = obj.getString("created");
                    
                    created = created.substring(0, 10);
                    
                    User user = new User();
                    user.set_id(userId);
                    user.setDisplayName(userDisplayName);
                    
                    list.add(new Article(articleId, content, title, created, user));
                }
                
                return list;
            } catch (JSONException e) {
                e.printStackTrace();
            }        
        }
        
        return null;
    }
    
    public void deleteArticle(String articleId)
    {
        String url = Constants.DELETE_EDIT_ARTICLE_URL + articleId;
        jsonStr = new DeleteRequest(Constants.SERVER_URL + url, null, activity).execute();
    }
    
    public void editArticle(Article article)
    {
        String url = Constants.DELETE_EDIT_ARTICLE_URL + article.get_id();
        gson = gsonBuilder.registerTypeAdapter(Article.class, new ArticleAdapter.EditArticleAdapter()).create();
        jsonStr = new PutRequest(Constants.SERVER_URL + url, gson.toJson(article), activity).execute();
    }
    
    public void addArticle(Article article)
    {
        String url = Constants.ADD_GET_ARTICLES_URL;
        gson = gsonBuilder.registerTypeAdapter(Article.class, new ArticleAdapter.AddArticleAdapter()).create();
        jsonStr = new PostRequest(Constants.SERVER_URL + url, gson.toJson(article), activity).execute();
    }

}
