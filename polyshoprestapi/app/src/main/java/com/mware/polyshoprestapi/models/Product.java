package com.mware.polyshoprestapi.models;

import java.io.Serializable;

public class Product implements Serializable {
	private String _id, photo, name, description, catsId;
	private int count, price;

	public Product() {
	}

	public Product(String _id, String photo, String name, String description, String catsId, int count, int price) {
		this._id = _id;
		this.photo = photo;
		this.name = name;
		this.description = description;
		this.catsId = catsId;
		this.count = count;
		this.price = price;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCatsId() {
		return catsId;
	}

	public void setCatsId(String catsId) {
		this.catsId = catsId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
