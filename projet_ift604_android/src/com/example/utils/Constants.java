package com.example.utils;

import com.google.android.gms.maps.model.LatLng;

public class Constants {
    
    // Use this on when working on emulator
    //public static final String SERVER_URL = "http://10.0.2.2:3000";
    
    // Use this one when working with phone (replace with your ipv4 address)
    public static final String SERVER_URL = "http://192.168.43.110:3000";
    
    // Urls
    public static final String SIGN_IN_URL = "/auth/signin";
    public static final String SIGN_OUT_URL = "/auth/signout";
    public static final String ADD_GET_ARTICLES_URL = "/articles";
    public static final String DELETE_EDIT_ARTICLE_URL = "/articles/";
    public static final String ADD_GET_HOUSES_URL = "/housetosells";
    public static final String DELETE_EDIT_HOUSE_URL = "/housetosells/";
    public static final String ADD_GET_USER_URL = "/auth/signup";    
    public static final String SEARCH_HOUSES = "/housetosells?category=%s&city=%s&maxPrice=%s&minPrice=%s";
    public static final String SEARCH_ALL_HOUSES = "/housetosells?category=&city=&maxPrice=999999999&minPrice=0";
    
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
    public static final String CATEGORY_EXTRA = "category_extra";
    public static final String CITY_EXTRA = "city_extra";
    public static final String PRIXMAX_EXTRA = "prixmax_extra";
    public static final String PRIXMIN_EXTRA = "prixmin_extra";
    
    // Used to browse images
    public static final int SELECT_PICTURE = 1;
    
    // Google Maps
    public static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    public static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    public static final String API_KEY = "YOUR_API_KEY";
    
    // Sherbrooke position
    public static final LatLng SHERBROOKE_POSITION = new LatLng(45.404476, -71.888351);
    
    // Other constants
    public static final String ALL_CATEGORIES = "All categories";

}
