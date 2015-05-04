package com.example.liquidsun.njamba;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;


public class RestaurantMealsActivity extends ActionBarActivity {


    private ListView mMealList;
    private EditText mFilter;
    private MealAdapter mAdapter;

    static ArrayList<Meal> meals = new ArrayList<Meal>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_meals);
        Intent it = getIntent();
        int id = it.getIntExtra("restaurant_id", 0);
        String restaurantName = it.getStringExtra("restaurant_name");
        Log.e("RestaurantID",String.valueOf(id));
        ListMeals mealFeed = ListMeals.getInstance();

        getSupportActionBar().setTitle(restaurantName);

        meals = mealFeed.mealsByRestaurant(id);

        Log.e("mealsList",String.valueOf(meals.size()));

        mMealList = (ListView) findViewById(R.id.list_view_restaurant_meal);
        mAdapter = new MealAdapter(meals);
        Log.e("Size Of Meals",String.valueOf(meals.size()));
        mMealList.setAdapter(mAdapter);


        mMealList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Meal clicked = meals.get(position);
                int mealId = clicked.getId();
                int restaurantId = clicked.getRestaurantId();

                Intent toSingleMeal = new Intent(RestaurantMealsActivity.this, SingleMealActivity.class);
                toSingleMeal.putExtra("mealId", mealId);
                toSingleMeal.putExtra("restaurantId", restaurantId);
                toSingleMeal.putExtra("name", clicked.getName());
                toSingleMeal.putExtra("price", clicked.getPrice());
                toSingleMeal.putExtra("imgPath", clicked.getImgLocation());
                startActivity(toSingleMeal);
            }
        });



    }


    private class MealAdapter extends ArrayAdapter<Meal> {
        public MealAdapter(ArrayList<Meal> meals) {
            super(RestaurantMealsActivity.this, 0, meals);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {

            Meal current = getItem(position);
            if (convertView == null) {
                convertView = RestaurantMealsActivity.this.getLayoutInflater()
                        .inflate(R.layout.meal_display_row, null);
            }


            TextView mealName = (TextView) convertView.findViewById(R.id.textview_name);
            mealName.setText(current.getName());

            TextView mealPrice = (TextView) convertView.findViewById(R.id.textview_price);

            mealPrice.setText("" + current.getPrice() + " KM");
            ImageView mealImage = (ImageView) convertView.findViewById(R.id.imageview_image);
            String img = getString(R.string.image_path) + current.getImgLocation();
            img = img.replaceAll("\\\\", "/");
            Log.d("IMGTAG Image", img);
            Picasso.with(getContext()).load(img).into(mealImage);
            return convertView;

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_restaurant_meals, menu);
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
}
