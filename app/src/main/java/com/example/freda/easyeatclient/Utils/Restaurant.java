package com.example.freda.easyeatclient.Utils;

/**
 * Created by freda on 8/29/16.
 */
public class Restaurant {
    String name;
    String addr;
    double mark;
    double price;
    String description;
    String tel;
    String img;
    String times;

    public Restaurant(){}

    public Restaurant(String name, String addr, double mark,  double price, String description, String tel, String pic, String times) {
        this.name = name;
        this.addr = addr;
        this.mark = mark;
        this.price = price;
        this.description = description;
        this.tel = tel;
        this.img = pic;
        this.times = times;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
    public String getAddr() {
        return addr;
    }


    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }


    public void setTimes(String times) {
        this.times = times;
    }
    public String getTimes() {
        return times;
    }



    public double getMark() {
        return mark;
    }
    public void setMark(double mark) {
        this.mark = mark;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getImg() {
        return img;
    }
    public void setImg(String picUrl) {
        this.img = picUrl;
    }


}
