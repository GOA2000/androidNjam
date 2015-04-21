package com.example.liquidsun.njamba.singletones;

import android.util.Log;

import com.example.liquidsun.njamba.Meal;
import com.example.liquidsun.njamba.Restaurant;
import com.example.liquidsun.njamba.Splashscreen;
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
public class ListRestaurants {
    private static ListRestaurants ourInstance = new ListRestaurants();

    public static ListRestaurants getInstance() {
        return ourInstance;
    }

    private ArrayList<Restaurant> mFeed;

    private ListRestaurants() {
        mFeed = new ArrayList<Restaurant>();
    }

    public void getFeed(String url){
        com.example.liquidsun.njamba.service.ServiceRequest.get(url, parseResponse());
    }

    public boolean  checkRestaurantlList(Restaurant currentRestaurant){

        for(Restaurant r :mFeed){
            if(currentRestaurant.getId()==r.getId()){
               return false;
            }

        }
        return true;


    }

    public ArrayList<Restaurant> getFeed(){
        return mFeed;
    }

    private Callback parseResponse(){
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
                            int id = postObj.getInt("id");
                            String name = postObj.getString("name");
                            String location = postObj.getString("location");
                            //String imgLocation = postObj.getString("image");

                            Restaurant currentRestaurant= new Restaurant(id,name,location);
                            if(true==checkRestaurantlList(currentRestaurant)){
                             mFeed.add(currentRestaurant

                             );

                             }



                            Log.e("ArraySizeRestaurant", String.valueOf(mFeed.size()));



                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

        };
    }
}
