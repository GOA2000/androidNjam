package com.example.liquidsun.njamba.singletones;

import android.util.Log;

import com.example.liquidsun.njamba.Meal;
import com.example.liquidsun.njamba.models.CartItem;
import com.example.liquidsun.njamba.service.ServiceRequest;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by neldindzekovic on 23/04/15.
 */
public class ListCartItems {

    private static ListCartItems ourInstance = new ListCartItems();
    private ArrayList<CartItem> mCartItems;


    public static ListCartItems getInstance() {
        return ourInstance;
    }


    private ListCartItems() {
        mCartItems = new ArrayList<CartItem>();
    }


    public ArrayList<CartItem> getListCartItems() {
        return mCartItems;
    }


    public void getCartItems(String url, String json) {
        ServiceRequest.post(url, json, parseResponse());
    }


    private Callback parseResponse() {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    JSONArray array = new JSONArray(response.body().string());

                    mCartItems = new ArrayList<CartItem>();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        int cartItemId = object.getInt("cart_item_id");
                        int cartId = object.getInt("cart_id");
                        int mealId = object.getInt("meal_id");
                        String mealName = object.getString("meal_name");
                        String mealRestaurant = object.getString("meal_restaurant");
                        double mealPrice = object.getDouble("meal_price");
                        int mealQuantity = object.getInt("meal_quantity");
                        double cartItemTotalPrice = object.getDouble("cart_item_totalPrice");
                        double cartTotalPrice = object.getDouble("cart_totalPrice");

                        CartItem currentItem = new CartItem(cartItemId, cartId, mealId, mealName, mealRestaurant, mealQuantity, mealPrice, cartItemTotalPrice, cartTotalPrice);
                        mCartItems.add(currentItem);
                    }

                    for (int i = 0; i < mCartItems.size(); i++) {
                        Log.e("CartItems" + i, mCartItems.get(i).toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

}
