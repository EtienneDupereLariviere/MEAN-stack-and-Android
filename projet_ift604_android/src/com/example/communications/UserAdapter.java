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
}
