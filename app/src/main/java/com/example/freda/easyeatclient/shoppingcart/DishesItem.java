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
    public int count;

    public DishesItem(int id, double price, String name, int typeId, String typeName) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.typeId = typeId;
        this.typeName = typeName;
        rating = new Random().nextInt(5) + 1;
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
