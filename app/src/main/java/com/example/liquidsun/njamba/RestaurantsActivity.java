package com.example.liquidsun.njamba;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liquidsun.njamba.service.ServiceRequest;
import com.example.liquidsun.njamba.singletones.ListMeals;
import com.example.liquidsun.njamba.singletones.ListRestaurants;
import com.example.liquidsun.njamba.v3.*;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class RestaurantsActivity extends ActionBarActivity {
    private int RestaurantId;
    private ListView mRestaurantList;
    private RestaurantAdapter mAdapter;
    private ListMeals restaurantMealList;

    static  ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        ListRestaurants restaurantFeed = ListRestaurants.getInstance();
        restaurantMealList = ListMeals.getInstance();
        restaurants =restaurantFeed.getFeed();


        mRestaurantList = (ListView)findViewById(R.id.list_view_restaurant);
        mAdapter = new RestaurantAdapter(restaurants);
        mRestaurantList.setAdapter(mAdapter);

        mRestaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        // Temporary Button
        Button buttonGoToAllMeals = (Button) findViewById(R.id.button_go_to_all_meals);
        buttonGoToAllMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAllMeals = new Intent(RestaurantsActivity.this, MealsActivity.class);
                startActivity(goToAllMeals);
            }
        });

        Button buttonGoToNewMain = (Button) findViewById(R.id.button_go_to_new_main);
        buttonGoToNewMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RestaurantsActivity.this, NewMainActivity.class);
                startActivity(i);
            }
        });

        Button buttonGoToV3 = (Button) findViewById(R.id.button_go_to_v3);
        buttonGoToV3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RestaurantsActivity.this, com.example.liquidsun.njamba.v3.MainActivity.class);
                startActivity(i);
            }
        });

        Button buttonGoToCart = (Button) findViewById(R.id.button_go_to_cart);
        buttonGoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RestaurantsActivity.this, CartActivity.class);
                startActivity(i);
            }
        });

    }


    private Callback getRestaurantMeals(){
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                makeToast(R.string.toast_try_again );
                Log.d("warning" , "warning fail");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                int restaurantId=-1;
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
                Intent toMeals = new Intent(RestaurantsActivity.this,RestaurantMealsActivity.class);
                toMeals.putExtra("restaurant_id",restaurantId);
                startActivity(toMeals);
            }
        };
    }

    private  class RestaurantAdapter extends ArrayAdapter<Restaurant> {
        public RestaurantAdapter(ArrayList<Restaurant> restaurants){
            super(RestaurantsActivity.this, 0,  restaurants );
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {

            Restaurant current = getItem(position);
            if (convertView == null) {
                convertView = RestaurantsActivity.this.getLayoutInflater()
                        .inflate(R.layout.restaurant_display_row, null);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_restaurants, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void makeToast(final int messageId){

        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RestaurantsActivity.this,
                                messageId,
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
