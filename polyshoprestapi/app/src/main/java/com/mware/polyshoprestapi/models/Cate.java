package com.mware.polyshoprestapi.models;

public class Cate {
	private String _id, name;

	public Cate() {
	}

	public Cate(String _id, String name) {
		this._id = _id;
		this.name = name;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
