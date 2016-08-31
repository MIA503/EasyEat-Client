package com.example.freda.easyeatclient.shoppingcart;

import android.util.SparseArray;

import java.security.Timestamp;
import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by lq on 30/08/16.
 */
public class OrderItem {
    private String orderId;
    private String restaurandId;
    private String userId;
    private Time time;
    private SparseArray<DishesItem> selectedList;
    private double price;

    public OrderItem() {
    }

    public OrderItem(String orderId, String restaurandId, String userId, Time time, SparseArray<DishesItem> selectedList, double price) {
        this.orderId = orderId;
        this.restaurandId = restaurandId;
        this.userId = userId;
        this.time = time;
        this.selectedList = selectedList;
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRestaurandId() {
        return restaurandId;
    }

    public void setRestaurandId(String restaurandId) {
        this.restaurandId = restaurandId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public SparseArray<DishesItem> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(SparseArray<DishesItem> selectedList) {
        this.selectedList = selectedList;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
