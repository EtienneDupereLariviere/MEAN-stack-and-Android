package com.example.communications;

import java.lang.reflect.Type;
import com.example.entity.User;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class UserAdapter {

    public static class SignInUserAdapter implements JsonSerializer<User> {
        public JsonElement serialize(User user, Type type, JsonSerializationContext jsc) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("username", user.getUsername());
            jsonObject.addProperty("password", user.getPassword());
            return jsonObject;
        }
    }
    
    public static class AddUserAdapter implements JsonSerializer<User> {
        public JsonElement serialize(User user, Type type, JsonSerializationContext jsc) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("displayName", user.getDisplayName());
            jsonObject.addProperty("firstName", user.getFirstName());
            jsonObject.addProperty("lastName", user.getLastName());
            jsonObject.addProperty("username", user.getUsername());
            jsonObject.addProperty("password", user.getPassword());
            jsonObject.addProperty("email", user.getEmail());
            
            return jsonObject;
        }
    }
}
