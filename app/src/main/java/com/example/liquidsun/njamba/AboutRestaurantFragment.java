package com.example.liquidsun.njamba;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.liquidsun.njamba.service.ServiceRequest;
import com.example.liquidsun.njamba.singletones.ListMeals;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutRestaurantFragment extends Fragment {

    private String restaurantName;
    private ListMeals restaurantMealList;

    public AboutRestaurantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_about_restaurant, container, false);

        Bundle arguments = getArguments();
        final int restaurantId = arguments.getInt("restaurantId");
        restaurantName = arguments.getString("restaurantName");
        String restaurantWorkingHours = arguments.getString("restaurantWorkingHours");
        String restaurantCity = arguments.getString("restaurantCity");
        String restaurantStreet = arguments.getString("restaurantStreet");

        TextView textViewRestaurantName = (TextView) v.findViewById(R.id.text_view_restaurant_name);
        textViewRestaurantName.setText(restaurantName);

        TextView textViewRestaurantWorkingHours = (TextView) v.findViewById(R.id.text_view_restaurant_working_hours);
        textViewRestaurantWorkingHours.setText(restaurantWorkingHours);

        TextView textViewRestaurantCity = (TextView) v.findViewById(R.id.text_view_restaurant_city);
        textViewRestaurantCity.setText(restaurantCity);

        TextView textViewRestaurantStreet = (TextView) v.findViewById(R.id.text_view_restaurant_street);
        textViewRestaurantStreet.setText(restaurantStreet);

        restaurantMealList = ListMeals.getInstance();

        Button goToRestaurant = (Button) v.findViewById(R.id.button_go_to_restaurant);
        goToRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getString(R.string.service_meals_for_restaurant);
                JSONObject clickedRestaurant = new JSONObject();
                try {
                    clickedRestaurant.put("id", Integer.toString(restaurantId));
                    Log.d("TAG", "JSON ID " + restaurantId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String json = clickedRestaurant.toString();
                Log.d("TAG", json);
                ServiceRequest.post(url, json, getRestaurantMeals());
            }
        });

        return v;
    }


    private Callback getRestaurantMeals(){
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("warning" , "warning fail");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                int restaurantId = -1;
                try {

                    JSONArray array = new JSONArray(response.body().string());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject postObj = array.getJSONObject(i);
                        restaurantId = postObj.getInt("restaurant_id");
                        int id = postObj.getInt("id");
                        String name = postObj.getString("name");
                        restaurantName = postObj.getString("restaurant");
                        double price = postObj.getDouble("price");
                        String imgLocation = postObj.getString("image");
                        String city = postObj.getString("restaurantCity");

                        Meal currentMeal= new Meal(restaurantId,id, name, restaurantName,city,price,imgLocation);

                        if (true == restaurantMealList.checkMealList(currentMeal)) {
                            restaurantMealList.mFeed.add(currentMeal);
                        }
                        Log.e("ArraySize", String.valueOf(restaurantMealList.mFeed.size()));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent toMeals = new Intent(getActivity(), RestaurantMealsActivity.class);
                toMeals.putExtra("restaurant_id",restaurantId);
                toMeals.putExtra("restaurant_name", restaurantName);
                startActivity(toMeals);
            }
        };
    }


}
