package com.example.liquidsun.njamba;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class SingleName extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_name);
        Intent it = getIntent();
        String name = it.getStringExtra("name");
        double price = it.getDoubleExtra("price",0);
        String imgPath = it.getStringExtra("imgPath");


        TextView   listName = (TextView) findViewById(R.id.meal_profile_title);
        TextView   listPrice= (TextView)  findViewById(R.id.meal_profile_price);
        ImageView listImage = (ImageView) findViewById(R.id.image_profile_picture);

        listName.setText(name);
        listPrice.setText(""+price+ " KM");
        Picasso.with(this).load(imgPath).into(listImage);





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
