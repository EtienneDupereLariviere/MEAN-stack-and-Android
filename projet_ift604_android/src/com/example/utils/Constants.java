package com.example.utils;

public class Constants {
    
    // Urls
    public static final String SERVER_URL = "http://10.0.2.2:3000";
    public static final String SIGN_IN_URL = "/auth/signin";
    public static final String SIGN_OUT_URL = "/auth/signout";
    public static final String ADD_GET_ARTICLES_URL = "/articles";
    public static final String DELETE_EDIT_ARTICLE_URL = "/articles/";

    // Request type
    public static final String PUT_REQUEST = "PUT_REQUEST";
    public static final String GET_REQUEST = "GET_REQUEST";
    public static final String POST_REQUEST = "POST_REQUEST";
    public static final String DELETE_REQUEST = "DELETE_REQUEST";
    
    // To know if the user is signed in
    public static final String COOKIES_HEADER = "Set-Cookie";
    public static final String COOKIE = "Cookie";
    public static final String USER_COOKIE = "cookie";
    public static final String IS_SIGNED_IN = "SignedIn";
    public static final String SIGNED_IN_USER_EMAIL ="SignedInUserEmail";
    public static final String SIGNED_IN_USERNAME ="SignedInUsername";
    public static final String SIGNED_IN_USER_ID ="SignedInUserId";
    
    // putExtra
    public static final String ARTICLE_EXTRA = "article_extra";

}
