package com.example.utils;

public enum ImageType {
    
    Png("data:image/png;base64,"),
    Jpeg("data:image/jpeg;base64,");
    
    private String type;
    
    public String getType()
    {
        return type;
    }
    
    ImageType(String type)
    {
        this.type = type;
    }
}
