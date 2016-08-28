package com.example.freda.easyeatclient.entity;

import java.util.List;

/**
 * Created by lq on 11/05/16.
 * <p/>
 * ProductType entity.
 */

public class ProductType implements java.io.Serializable {

    // Fields

    private String id;
    private Integer shopID;
    private String typeName;
    private List<Product> productList;

    // Constructors

    /**
     * default constructor
     */
    public ProductType() {
    }

    /**
     * minimal constructor
     */
    public ProductType(Integer shopID) {
        this.shopID = shopID;
    }

    /**
     * full constructor
     */
    public ProductType(Integer shopID, String typeName) {
        this.shopID = shopID;
        this.typeName = typeName;
    }

    // Property accessors

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Integer getShopID() {
        return shopID;
    }

    public void setShopID(Integer shopID) {
        this.shopID = shopID;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

}