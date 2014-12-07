package com.example.entity;

public class Categorie {

	String categorieName;
	String categorieDesc;
	String _id;
	
	public Categorie(){
		super();
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
