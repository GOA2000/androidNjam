package com.example.liquidsun.njamba;

/**
 * Created by Liquid Sun on 16/04/2015.
 */
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Toast;

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


public class MealsActivity extends ActionBarActivity {

    private ListView mMealList;
    private EditText mFilter;
    private MealAdapter mAdapter;

    static  ArrayList<Meal> meals = new ArrayList<Meal>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_meal_list);

        ListMeals mealFeed = ListMeals.getInstance();
        mealFeed.getFeed( getString(R.string.service_meals) );

        meals= mealFeed.getFeed();


        mMealList = (ListView)findViewById(R.id.list_view_meal);
        mAdapter = new MealAdapter(meals);
        mMealList.setAdapter(mAdapter);

        mMealList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Meal clicked = meals.get(position);
                int mealId = clicked.getId();
                String url = getString(R.string.service_single_meal);
                JSONObject clickedMeal = new JSONObject();
                try {
                    clickedMeal.put("id", Integer.toString(mealId));
                    Log.d("TAG", "JSON ID " +id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String json = clickedMeal.toString();
                Log.d("TAG", json);
                ServiceRequest.post(url, json, getMealsImg());
            }
        });



        mFilter = (EditText)findViewById(R.id.edit_text_filter);
        mFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ( (ArrayAdapter<Meal>)mMealList.getAdapter()).getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_meals, menu);
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


    private  class MealAdapter extends ArrayAdapter<Meal>{
        public MealAdapter(ArrayList<Meal> meals){
            super(MealsActivity.this, 0,  meals );
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {

            Meal current = getItem(position);
            if (convertView == null) {
                convertView = MealsActivity.this.getLayoutInflater()
                        .inflate(R.layout.meal_display_row, null);
            }


            TextView mealName = (TextView) convertView.findViewById(R.id.textview_name);
            mealName.setText(current.getName());

            TextView mealPrice = (TextView) convertView.findViewById(R.id.textview_price);

            mealPrice.setText("" + current.getPrice() + " KM");
            ImageView mealImage = (ImageView) convertView.findViewById(R.id.imageview_image);
            String img =getString(R.string.image_path) + current.getImgLocation();
            img = img.replaceAll("\\\\","/");
            Log.d("IMGTAG Image", img);
            Picasso.with(getContext()).load(img).into(mealImage);
            return convertView;

        }
    }

    private Callback getMealsImg(){
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                makeToast(R.string.toast_try_again );
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


                        Intent goToMeal = new Intent(MealsActivity.this,SingleMealActivity.class);
                        goToMeal.putExtra("name", name);
                        goToMeal.putExtra("price", price);
                        goToMeal.putExtra("imgPath", imgLocation);
                        startActivity(goToMeal);
                    }
                } catch (JSONException e) {
                    makeToast(R.string.toast_try_again);
                    e.printStackTrace();
                }
            }
        };
    }

    private void makeToast(final int messageId){

        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MealsActivity.this,
                                messageId,
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
