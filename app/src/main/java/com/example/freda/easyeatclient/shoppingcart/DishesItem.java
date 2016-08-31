package com.example.freda.easyeatclient.shoppingcart;

import java.util.ArrayList;
import java.util.Random;

public class DishesItem {
    public int id;
    public int typeId;
    public int rating;
    public String name;
    public String typeName;
    public double price;
    public String description;
    public String img;
    public int count;

    public DishesItem(int id, double price, String name, int typeId, String typeName) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.typeId = typeId;
        this.typeName = typeName;
        rating = new Random().nextInt(5) + 1;
    }

    public DishesItem(int id, int typeId, int rating, String name, String typeName, double price, String description, String img, int count) {
        this.id = id;
        this.typeId = typeId;
        this.rating = rating;
        this.name = name;
        this.typeName = typeName;
        this.price = price;
        this.description = description;
        this.img = img;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private static ArrayList<DishesItem> DishesList;
    private static ArrayList<DishesItem> typeList;

    //// TODO: 29/08/16
    private static void initData() {
        DishesList = new ArrayList<>();
        typeList = new ArrayList<>();
        DishesItem item = null;
        for (int i = 1; i < 15; i++) {
            for (int j = 1; j < 10; j++) {
                item = new DishesItem(100 * i + j, Math.random() * 100, "dish" + (100 * i + j), i, "type" + i);
                DishesList.add(item);
            }
            typeList.add(item);
        }
    }

    public static ArrayList<DishesItem> getDishesList() {
        if (DishesList == null) {
            initData();
        }
        return DishesList;
    }

    public static ArrayList<DishesItem> getTypeList() {
        if (typeList == null) {
            initData();
        }
        return typeList;
    }
}
