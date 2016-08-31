package com.example.freda.easyeatclient.ClientAdmin;


import java.net.URI;

public class User {
    private String uid;
    private String name;
    private String email;
    private String photo;
    private String phone;


    public User() {

    }

    public User(String uid, String name, String email, String photo, String phone) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.photo = photo;
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
