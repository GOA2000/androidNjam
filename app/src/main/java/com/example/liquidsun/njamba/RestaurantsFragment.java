package com.example.liquidsun.njamba;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liquidsun.njamba.service.ServiceRequest;
import com.example.liquidsun.njamba.singletones.ListMeals;
import com.example.liquidsun.njamba.singletones.ListRestaurants;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantsFragment extends Fragment {

    private ListView mRestaurantsList;
    private RestaurantAdapter mAdapter;
    private ListMeals restaurantMealList;

    static ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();


    public RestaurantsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_restaurants, container, false);

        mRestaurantsList = (ListView) v.findViewById(R.id.list_view_restaurants);
        restaurantMealList = ListMeals.getInstance();

        ListRestaurants restaurantsFeed = ListRestaurants.getInstance();
        restaurants = restaurantsFeed.getFeed();

        mAdapter = new RestaurantAdapter(restaurants);

        mRestaurantsList.setAdapter(mAdapter);

        mRestaurantsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant clicked = restaurants.get(position);
                int restaurantId = clicked.getId();
                String url = getString(R.string.service_meals_for_restaurant);
                JSONObject clickedRestaurant = new JSONObject();
                try {
                    clickedRestaurant.put("id", Integer.toString(restaurantId));
                    Log.d("TAG", "JSON ID " + id);
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


    private  class RestaurantAdapter extends ArrayAdapter<Restaurant> {
        public RestaurantAdapter(ArrayList<Restaurant> restaurants){
            super(getActivity(), 0,  restaurants );
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {

            Restaurant current = getItem(position);
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.restaurant_display_row, null);
            }


            TextView restaurantName = (TextView) convertView.findViewById(R.id.text_view_restaurant_name);
            restaurantName.setText(current.getName());

            TextView restaurantCity = (TextView) convertView.findViewById(R.id.text_view_restaurant_city);
            restaurantCity.setText(current.getLocation());

            // ImageView mealImage = (ImageView) convertView.findViewById(R.id.imageview_image);
            //  String img =getString(R.string.image_path) + current.getImgLocation();
            //  img = img.replaceAll("\\\\","/");
            //  Log.d("IMGTAG Image", img);
            //   Picasso.with(getContext()).load(img).into(mealImage);
            return convertView;

        }
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
                        restaurantId = Integer.parseInt(postObj.getString("restaurant_id"));
                        int id = postObj.getInt("id");
                        String name = postObj.getString("name");
                        String restaurantName = postObj.getString("restaurant");
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
                startActivity(toMeals);
            }
        };
    }

}
