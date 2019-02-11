package com.example.firstproject_eat.datamodels;

public class Product {

    private String name, description, image;
    private float price;
    private int quantity = 0;

    //IMMAGINE

    public Product(String name, String description, String image, float price){
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantity(){
        this.quantity++;
    }

    public void decreaseQuantity(){
        if(quantity == 0){
            return;
        }
        this.quantity--;
    }

    public float getSubtotal(){
        return quantity * price;
    }
}
