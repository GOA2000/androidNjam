package com.example.liquidsun.njamba;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liquidsun.njamba.models.CartItem;
import com.example.liquidsun.njamba.singletones.ListCartItems;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    private ListView mListViewCartItems;
    private ArrayList<CartItem> mArrayListCartItems;
    private CartItemsAdapter mCartItemsAdapter;


    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_cart, container, false);

        ListCartItems listCartItems = ListCartItems.getInstance();

        mArrayListCartItems = listCartItems.getListCartItems();

        mListViewCartItems = (ListView) v.findViewById(R.id.list_view_cart_items);
        mCartItemsAdapter = new CartItemsAdapter(mArrayListCartItems);
        mListViewCartItems.setAdapter(mCartItemsAdapter);

        Button checkout = (Button) v.findViewById(R.id.button_checkout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PaymentActivity.class);
                startActivity(i);
            }
        });

        return v;
    }


    private class CartItemsAdapter extends ArrayAdapter<CartItem> {

        public CartItemsAdapter(ArrayList<CartItem> cartItems) {
            super(getActivity(), 0, cartItems);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            CartItem current = getItem(position);

            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.cart_items_row, null);
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

}
