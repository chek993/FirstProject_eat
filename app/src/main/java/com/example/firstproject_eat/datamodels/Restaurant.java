package com.example.firstproject_eat.datamodels;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Restaurant {

    private String name, address, image, id;
    private float minOrder;

    private ArrayList<Product> products = new ArrayList<>();

    public static final String ENDPOINT = "restaurants/";

    public Restaurant(String name, String address, float minOrder, String image){
        this.name = name;
        this.address = address;
        this.minOrder = minOrder;
        this.image = image;
    }

    public Restaurant(JSONObject jsonRestaurant) throws JSONException {
        name = jsonRestaurant.getString("name");
        address = jsonRestaurant.getString("address");
        minOrder = Float.valueOf(jsonRestaurant.getString("min_order"));
        image = jsonRestaurant.getString("image_url");
        id = jsonRestaurant.getString("id");
        products = jsonToProducts(jsonRestaurant.getJSONArray("products"));
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private ArrayList<Product> jsonToProducts(JSONArray jsonArray) throws JSONException {
        ArrayList<Product> arrayList = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){
            arrayList.add(new Product(jsonArray.getJSONObject(i)));
        }
        return arrayList;
    }
}
