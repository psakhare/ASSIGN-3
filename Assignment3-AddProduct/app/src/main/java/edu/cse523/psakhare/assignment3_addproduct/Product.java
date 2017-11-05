package edu.cse523.psakhare.assignment3_addproduct;

import android.net.Uri;

/**
 * Created by Priyanka on 11/3/2017.
 */

public class Product {
    private String name = null;
    private String price = null;
    private Uri image = null;

    public Product(String name, String price, Uri image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public Uri getImage() {
        return image;
    }
    public void setImage(Uri image) {
        this.image = image;
    }
}
