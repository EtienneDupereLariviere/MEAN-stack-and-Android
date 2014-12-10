package com.example.entity;

import java.util.ArrayList;
import java.util.List;

public class CategorieCollection {

    private static List<Categorie> listCat = new ArrayList<Categorie>();
    
    public static void addToList(Categorie cat) {
        listCat.add(cat);
    }
    
    public static List<Categorie> getAllCategories()
    {
        return listCat;
    }
    
    public static Categorie findCategorie(String catName)
    {
        for (Categorie cat : listCat) {
            if(cat.getCategorieName().equals(catName)){
                return cat; 
            }
        }
        
        return null;
    }
    
    public static List<String> getAllCategoryNames()
    {
        List<String> listStr = new ArrayList<String>();
        
        for(int i = 0; i < listCat.size(); i++)
            listStr.add(listCat.get(i).getCategorieName());
        
        return listStr;
    }

}
