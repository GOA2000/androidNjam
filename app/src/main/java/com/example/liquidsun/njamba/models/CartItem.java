package com.example.liquidsun.njamba.models;

/**
 * Created by neldindzekovic on 23/04/15.
 */
public class CartItem {

    private int mCartItemId;
    private int mCartId;
    private int mMealId;
    private String mMealName;
    private String mMealRestaurant;
    private double mMealPrice;
    private int mMealQuantity;
    private double mCartItemTotalPrice;
    private double mCartTotalPrice;


    public CartItem(int cartItemId, int cartId, int mealId, String mealName, String mealRestaurant, int mealQuantity, double mealPrice, double cartItemTotalPrice, double cartTotalPrice) {
        mCartItemId = cartItemId;
        mCartId = cartId;
        mMealId = mealId;
        mMealName = mealName;
        mMealRestaurant = mealRestaurant;
        mMealQuantity = mealQuantity;
        mMealPrice = mealPrice;
        mCartItemTotalPrice = cartItemTotalPrice;
        mCartTotalPrice = cartTotalPrice;
    }


    @Override
    public String toString() {
        return "CartItem{" +
                "mCartItemId=" + mCartItemId +
                ", mCartId=" + mCartId +
                ", mMealId=" + mMealId +
                ", mMealName='" + mMealName + '\'' +
                ", mMealRestaurant='" + mMealRestaurant + '\'' +
                ", mMealPrice=" + mMealPrice +
                ", mMealQuantity=" + mMealQuantity +
                ", mCartItemTotalPrice=" + mCartItemTotalPrice +
                ", mCartTotalPrice=" + mCartTotalPrice +
                '}';
    }


    public int getCartItemId() {
        return mCartItemId;
    }


    public void setCartItemId(int cartItemId) {
        mCartItemId = cartItemId;
    }


    public int getCartId() {
        return mCartId;
    }


    public void setCartId(int cartId) {
        mCartId = cartId;
    }


    public int getMealId() {
        return mMealId;
    }


    public void setMealId(int mealId) {
        mMealId = mealId;
    }


    public String getMealName() {
        return mMealName;
    }


    public void setMealName(String mealName) {
        mMealName = mealName;
    }


    public String getMealRestaurant() {
        return mMealRestaurant;
    }


    public void setMealRestaurant(String mealRestaurant) {
        mMealRestaurant = mealRestaurant;
    }


    public double getMealPrice() {
        return mMealPrice;
    }


    public void setMealPrice(double mealPrice) {
        mMealPrice = mealPrice;
    }


    public int getMealQuantity() {
        return mMealQuantity;
    }


    public void setMealQuantity(int mealQuantity) {
        mMealQuantity = mealQuantity;
    }


    public double getCartItemTotalPrice() {
        return mCartItemTotalPrice;
    }


    public void setCartItemTotalPrice(double cartItemTotalPrice) {
        mCartItemTotalPrice = cartItemTotalPrice;
    }


    public double getCartTotalPrice() {
        return mCartTotalPrice;
    }


    public void setCartTotalPrice(double cartTotalPrice) {
        mCartTotalPrice = cartTotalPrice;
    }

}
