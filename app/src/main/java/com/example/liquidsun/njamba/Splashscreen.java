package com.example.liquidsun.njamba;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.liquidsun.njamba.singletones.ListRestaurants;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class Splashscreen extends ActionBarActivity {
    static  ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        ListRestaurants singletoneRestaurantList= ListRestaurants.getInstance();
        singletoneRestaurantList.getFeed("http://192.168.0.11:9000/api/restaurants");
        restaurants=singletoneRestaurantList.getFeed();


      Intent intent = new Intent(this,RestaurantsActivity.class);


        startActivity(intent);
        finish();
          }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splashscreen, menu);
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
