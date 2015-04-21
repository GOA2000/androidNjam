package com.example.liquidsun.njamba;

/**
 * Created by Liquid Sun on 15/04/2015.
 */
public class Meal {



    private int mId;
    private String mName;
    private String mRestaurant;
    private String mCity;
    private double  mPrice;
    private String mImgLocation;

    public Meal(int id, String name) {
        mId = id;
        mName = name;
       // mContent = content;
    }

    public Meal(int id,String name, String restaurant, String city, double price, String imgLocation) {
        mId = id;
        mName = name;
        mRestaurant = restaurant;
        mCity = city;
        mPrice =price;
        mImgLocation=imgLocation;

    }

    public String toString(){
        return mName +" \n "+ "Price: " + mPrice;
    }


    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public String getTitle() {
        return mName;
    }

    public void setTitle(String title) {
        mName = title;
    }

    public String getName() {
        return mName;
    }

    public String getImgLocation() {
        return mImgLocation;
    }

    public void setImgLocation(String imgLocation) {
        mImgLocation = imgLocation;
    }

    public String getRestaurantName() {
        return mRestaurant;
    }

    public String getRestaurantCity() {
        return mCity;
    }

}


