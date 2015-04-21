package com.example.liquidsun.njamba;

import android.content.Intent;
import android.media.MediaRouter;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.example.liquidsun.njamba.service.ServiceRequest;
import com.example.liquidsun.njamba.singletones.ListRestaurants;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class Splashscreen extends ActionBarActivity {
    static  ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
    private ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        ProgressBar spinner;
        spinner = (ProgressBar)findViewById(R.id.progressBar1);

        ListRestaurants singletoneRestaurantList= ListRestaurants.getInstance();
        singletoneRestaurantList.getFeed(getText(R.string.service_restaurants).toString());
        restaurants=singletoneRestaurantList.getFeed();
        ServiceRequest.post(getText(R.string.localHost).toString(),"{}",toIntent());



    }


    public Callback toIntent(){


        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Intent intent = new Intent(Splashscreen.this, ErrorLoading.class);

                startActivity(intent);

                finish();

            }

            @Override
            public void onResponse(Response response) throws IOException {

                Intent intent = new Intent(Splashscreen.this, RestaurantsActivity.class);

                startActivity(intent);

                finish();
            }
        };

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
