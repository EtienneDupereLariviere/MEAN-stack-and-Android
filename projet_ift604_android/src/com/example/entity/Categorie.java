package com.example.entity;

import java.io.Serializable;

public class Categorie implements Serializable {

    private static final long serialVersionUID = 1L;
    
    String categorieName;
	String categorieDesc;
	String _id;
	
	public Categorie(){
		super();
	}
	
	public Categorie(String categorieName, String categorieDesc, String _id) {
	    this.categorieName = categorieName;
	    this.categorieDesc = categorieDesc;
	    this._id = _id;
	    
	    CategorieCollection.addToList(this);
	}
	
	public String getCategorieName() {
		return categorieName;
	}

	public void setCategorieName(String categorieName) {
		this.categorieName = categorieName;
	}

	public String getCategorieDesc() {
		return categorieDesc;
	}

	public void setCategorieDesc(String categorieDesc) {
		this.categorieDesc = categorieDesc;
	}
	
	public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}

