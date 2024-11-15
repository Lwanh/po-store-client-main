package com.mware.polyshoprestapi.models;

public class User {
    private String _id, fullName, email, password;
    private int wallet;

    public User() {
    }

    public User(String _id, String fullName, String email, String password, int wallet) {
        this._id = _id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.wallet = wallet;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }
}
