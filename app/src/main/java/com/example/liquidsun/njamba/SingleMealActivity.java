package com.example.liquidsun.njamba;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liquidsun.njamba.service.ServiceRequest;
import com.example.liquidsun.njamba.singletones.ListCartItems;
import com.example.liquidsun.njamba.singletones.UserData;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class SingleMealActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_meal);
        Intent it = getIntent();
        final int id = it.getIntExtra("id", -1);
        String name = it.getStringExtra("name");
        double price = it.getDoubleExtra("price",0);
        String imgPath = it.getStringExtra("imgPath");


        TextView   listName = (TextView) findViewById(R.id.meal_profile_title);
        TextView   listPrice= (TextView)  findViewById(R.id.meal_profile_price);
        ImageView listImage = (ImageView) findViewById(R.id.image_profile_picture);

        listName.setText(name);
        listPrice.setText(""+price+ " KM");
        Picasso.with(this).load(imgPath).into(listImage);

        Button addToCart = (Button) findViewById(R.id.button_add_to_cart);

        final UserData userData = UserData.getInstance();

        if (userData.getId() != -1) {
            addToCart.setText("Add to cart");
            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = getString(R.string.service_add_to_cart);
                    JSONObject jsonId = new JSONObject();
                    try {
                        jsonId.put("id", Integer.toString(id));
                        jsonId.put("user_id", Integer.toString(userData.getId()));
                        Log.e("id jela za cart", " " + id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String jsonIdString = jsonId.toString();
                    ListCartItems.getInstance().getCartItems(url, jsonIdString);

                    Intent i = new Intent(SingleMealActivity.this, CartActivity.class);
                    i.putExtra("toCart", 1);
                    startActivity(i);
                }
            });
        } else {
            addToCart.setText("Login to Add to Cart");
            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(SingleMealActivity.this, MainActivity.class);
                    startActivity(i);

                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_name, menu);
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
