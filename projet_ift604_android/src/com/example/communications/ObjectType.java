package com.example.communications;

public enum ObjectType {
    
    Article("1"),
    Categorie("2"),
    Maison("3"),
    User("4");
    
    private String type;
    
    public String getType()
    {
        return type;
    }
    
    ObjectType(String type)
    {
        this.type = type;
    }
}
