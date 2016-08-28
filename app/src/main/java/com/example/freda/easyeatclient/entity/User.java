package com.example.freda.easyeatclient.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lq on 12/05/16.
 */
public class User {
    private String id;
    private String name;
    private String password;
    private String imgName;
    private String imgPath;
    private Set<FileImage> fileImages = new HashSet<FileImage>(0);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Set<FileImage> getFileImages() {
        return fileImages;
    }

    public void setFileImages(Set<FileImage> fileImages) {
        this.fileImages = fileImages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
