package com.example.liquidsun.njamba.singletones;

import android.util.Log;

import com.example.liquidsun.njamba.Meal;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Liquid Sun on 16/04/2015.
 */
public class ListMeals {
    private static ListMeals ourInstance = new ListMeals();

    public static ListMeals getInstance() {
        return ourInstance;
    }

    public ArrayList<Meal> mFeed;

    private ListMeals() {
        mFeed = new ArrayList<Meal>();
    }


    public void getFeed(String url) {
        com.example.liquidsun.njamba.service.ServiceRequest.get(url, parseResponse());
    }

    public ArrayList<Meal> mealsByRestaurant(int id) {

        ArrayList<Meal> returnMeals = new ArrayList<>();

        for (Meal m : mFeed) {
            if (m.getRestaurantId() == id) {
                returnMeals.add(m);
            }
        }
        return returnMeals;

    }


    public boolean checkMealList(Meal currentMeal) {

        for (Meal m : mFeed) {
            if (currentMeal.getId() == m.getId() && currentMeal.getRestaurantId() == m.getRestaurantId()) {
                return false;
            }

        }
        return true;


    }

    public ArrayList<Meal> getFeed() {
        return mFeed;
    }

    public Callback parseResponse() {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("RESPONSE", e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {

                try {

                    JSONArray array = new JSONArray(response.body().string());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject postObj = array.getJSONObject(i);
                        int restaurantId = postObj.getInt("restaurant_id");
                        int id = postObj.getInt("id");
                        String name = postObj.getString("name");
                        double price = postObj.getDouble("price");
                        String imgLocation = postObj.getString("image");

                        Meal currentMeal = new Meal(restaurantId, id, name, price, imgLocation);

                        if (true == checkMealList(currentMeal)) {
                            mFeed.add(currentMeal);
                        }
                        Log.e("ArraySize", String.valueOf(mFeed.size()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };
    }
}
