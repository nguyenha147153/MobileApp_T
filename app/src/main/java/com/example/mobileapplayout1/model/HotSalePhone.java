package com.example.mobileapplayout1.model;

import java.io.Serializable;

public class HotSalePhone implements Serializable {
    String id;
    String description;
    String brand;
    String productName;
    int productPrice;
    String urlImg;
    int numberProduct =1;


    public HotSalePhone(){

    }

    public String getBrand(){return brand;}
    public void setBrand(String brand){ this.brand = brand;}

    public String getDescription(){return description;}
    public void setDescription(String description){ this.description=description;}

    public int getNumProduct() {
        return numberProduct;
    }
    public void setNumProduct(int numberProduct) {
        this.numberProduct = numberProduct;
    }

    public String getId(){return id;}
    public void setId(String id){this.id=id;}

    public String getProductName() {
        return productName;
    }
    public void setProductName(String name) {
        this.productName = name;
    }

    public int getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(int price) {
        this.productPrice = price;
    }

    public String getUrlImg() {
        return urlImg;
    }
    public void setUrlImg(String imageUrl) {
        this.urlImg = imageUrl;
    }


}
