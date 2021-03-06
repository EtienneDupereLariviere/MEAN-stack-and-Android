package com.example.entity;

import java.io.Serializable;

import com.example.utils.ImageType;

public class Maison implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    Double latitude;
	Double longitude;
	Categorie categorie;
	String address;
	String description;
	String caracteristic;
	Integer nbChambre;
	Integer price;
	byte[] image;
	User user;
	String _id;
	ImageType imageType;
	
	public Maison(){
		super();
	}
	
	public Maison(Categorie categorie, Integer price, String description, byte[] image, String caracteristic,
			      double longitude, double latitude, Integer nbChambre, String address, User user, String _id){
		this.categorie = categorie;
		this.price = price;
		this.description = description;
		this.image = image;
		this.caracteristic = caracteristic;
		this.longitude = longitude;
		this.latitude = latitude;
		this.nbChambre = nbChambre;
		this.address = address;
		this.user = user;
		this._id = _id;
	}
	
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCaracteristic() {
		return caracteristic;
	}

	public void setCaracteristic(String caracteristic) {
		this.caracteristic = caracteristic;
	}

	public Integer getNbChambre() {
		return nbChambre;
	}

	public void setNbChambre(Integer nbChambre) {
		this.nbChambre = nbChambre;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getImageType() {
        return imageType.getType();
    }

    public void setImageType(ImageType imageType) {
        this.imageType = imageType;
    }

}
