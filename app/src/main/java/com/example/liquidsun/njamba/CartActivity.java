package com.example.liquidsun.njamba;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liquidsun.njamba.models.CartItem;
import com.example.liquidsun.njamba.singletones.ListCartItems;

import java.util.ArrayList;


public class CartActivity extends ActionBarActivity {

    private ListView mListViewCartItems;
    private ArrayList<CartItem> mArrayListCartItems;
    private CartItemsAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ListCartItems listCartItems = ListCartItems.getInstance();

        //mArrayListCartItems = new ArrayList<CartItem>();
        mArrayListCartItems = listCartItems.getListCartItems();

        mListViewCartItems = (ListView) findViewById(R.id.list_view_cart_items);
        mAdapter = new CartItemsAdapter(mArrayListCartItems);
        mListViewCartItems.setAdapter(mAdapter);

        Button buttonCheckout = (Button) findViewById(R.id.button_checkout);
        buttonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CartActivity.this, PaymentActivity.class);
                startActivity(i);
            }
        });
    }


    private class CartItemsAdapter extends ArrayAdapter<CartItem> {

        public CartItemsAdapter(ArrayList<CartItem> cartItems) {
            super(CartActivity.this, 0, cartItems);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            CartItem current = getItem(position);

            if (convertView == null) {
                convertView = CartActivity.this.getLayoutInflater().inflate(R.layout.cart_items_row, null);
            }

            TextView mealName = (TextView) convertView.findViewById(R.id.text_view_cart_item_meal_name);
            mealName.setText(current.getMealName());

            TextView mealRestaurant = (TextView) convertView.findViewById(R.id.text_view_cart_item_meal_restaurant);
            mealRestaurant.setText(current.getMealRestaurant());

            TextView mealPrice = (TextView) convertView.findViewById(R.id.text_view_cart_item_meal_price);
            mealPrice.setText("" + current.getMealPrice());

            TextView mealQuantity = (TextView) convertView.findViewById(R.id.text_view_cart_item_meal_quantity);
            mealQuantity.setText("" + current.getMealQuantity());

            TextView mealTotal = (TextView) convertView.findViewById(R.id.text_view_cart_item_meal_total);
            mealTotal.setText("" + current.getCartItemTotalPrice());

            return convertView;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart, menu);
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
