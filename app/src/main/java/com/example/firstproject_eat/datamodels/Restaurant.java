package com.example.firstproject_eat.datamodels;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Restaurant {

    private String name, address, image;
    private float minOrder;

    private ArrayList<Product> products;

    public Restaurant(String name, String address, float minOrder, String image){
        this.name = name;
        this.address = address;
        this.minOrder = minOrder;
        this.image = image;

        products = new ArrayList<>();
    }

    public Restaurant(JSONObject jsonRestaurant) throws JSONException {
        name = jsonRestaurant.getString("name");
        address = jsonRestaurant.getString("address");
        minOrder = Float.valueOf(jsonRestaurant.getString("min_order"));
        image = jsonRestaurant.getString("image_url");

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getMinOrder() {
        return minOrder;
    }

    public void setMinOrder(float minOrder) {
        this.minOrder = minOrder;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
