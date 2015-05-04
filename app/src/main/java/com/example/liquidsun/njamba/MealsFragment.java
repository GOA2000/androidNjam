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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liquidsun.njamba.service.ServiceRequest;
import com.example.liquidsun.njamba.singletones.ListMeals;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MealsFragment extends Fragment {

    private ListView mMealsList;
    private MealAdapter mMealAdapter;
    private EditText mMealFilter;
    private ArrayList<Meal> mMeals = new ArrayList<Meal>();


    public MealsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_meals, container, false);

        mMealsList = (ListView) v.findViewById(R.id.list_view_meals);

        ListMeals mealFeed = ListMeals.getInstance();
        mealFeed.getFeed(getString(R.string.service_meals));

        mMeals = mealFeed.getFeed();

        mMealAdapter = new MealAdapter(mMeals);

        mMealsList.setAdapter(mMealAdapter);

        mMealsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Meal clicked = mMeals.get(position);
                int mealId = clicked.getId();
                String url = getString(R.string.service_single_meal);
                JSONObject clickedMeal = new JSONObject();
                try {
                    clickedMeal.put("id", Integer.toString(mealId));
                    Log.d("TAG", "JSON ID " + id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String json = clickedMeal.toString();
                Log.d("TAG", json);
                ServiceRequest.post(url, json, getMealsImg());
            }
        });

        mMealFilter = (EditText) v.findViewById(R.id.edit_text_filter);

        return v;
    }


    private Callback getMealsImg(){
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("warning" , "warning fail");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String responseJson = response.body().string();
                try {
                    JSONObject meal = new JSONObject(responseJson);
                    int id = meal.getInt("id");
                    if(id > 0){
                        String name = meal.getString("name");
                        double price = meal.getDouble("price");
                        String imgLocation;

                        imgLocation = getString(R.string.image_path) + meal.getString("image");
                        imgLocation = imgLocation.replaceAll("\\\\", "/");


                        Intent goToMeal = new Intent(getActivity(), NewMealActivity.class);
                        goToMeal.putExtra("id", id);
                        goToMeal.putExtra("name", name);
                        goToMeal.putExtra("price", price);
                        goToMeal.putExtra("imgPath", imgLocation);
                        startActivity(goToMeal);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }


    private class MealAdapter extends ArrayAdapter<Meal> {

        public MealAdapter(ArrayList<Meal> meals) {
            super(getActivity(), 0, meals);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            Meal current = getItem(position);

            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.custom_meals_list_row, null);
            }

            TextView mealName = (TextView) convertView.findViewById(R.id.text_view_meal_name);
            mealName.setText(current.getName());

            TextView mealRestaurant = (TextView) convertView.findViewById(R.id.text_view_meal_restaurant_name);
            mealRestaurant.setText(current.getRestaurantName());

            TextView mealRestaurantCity = (TextView) convertView.findViewById(R.id.text_view_meal_restaurant_city);
            mealRestaurantCity.setText(current.getRestaurantCity());

            TextView mealPrice = (TextView) convertView.findViewById(R.id.text_view_meal_price);
            mealPrice.setText("" + current.getPrice());

            ImageView mealImage = (ImageView) convertView.findViewById(R.id.image_view_meal);
            String img = getString(R.string.image_path) + current.getImgLocation();
            img = img.replaceAll("\\\\", "/");
            Log.d("IMGTAG Image", img);
            Picasso.with(getContext()).load(img).into(mealImage);

            return convertView;
        }

    }

}
