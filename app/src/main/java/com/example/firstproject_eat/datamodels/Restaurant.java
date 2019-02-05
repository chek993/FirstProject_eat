package com.example.firstproject_eat.datamodels;

public class Restaurant {

    private String name, address, image;
    private float minOrder;

    public Restaurant(String name, String address, float minOrder, String image){
        this.name = name;
        this.address = address;
        this.minOrder = minOrder;
        this.image = image;
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
}
