package com.example.communications;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import entity.Article;

public class ArticleAdapter {

    public static class EditArticleAdapter implements JsonSerializer<Article> {
        public JsonElement serialize(Article article, Type type, JsonSerializationContext jsc) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("_id", article.get_id());
            jsonObject.addProperty("content", article.getContent());
            jsonObject.addProperty("title", article.getTitle());
 
            JsonObject user = new JsonObject();
            user.addProperty("_id", article.getUser().get_id());
            user.addProperty("displayName", article.getUser().getDisplayName());
            
            jsonObject.add("user", user);
            
            return jsonObject;
        }
    }
    
    public static class AddArticleAdapter implements JsonSerializer<Article> {
        public JsonElement serialize(Article article, Type type, JsonSerializationContext jsc) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("content", article.getContent());
            jsonObject.addProperty("title", article.getTitle());
            
            return jsonObject;
        }
    }
}
