package com.example.liquidsun.njamba;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liquidsun.njamba.singletones.ListCartItems;
import com.example.liquidsun.njamba.singletones.UserData;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private int mealId;
    private String mealName;

    public static final String MAIN_FRAGMENT_KEY = "ba.bitcamp.neldin.main_fragment_key";

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        Bundle arguments = getArguments();
        int position = arguments.getInt(MAIN_FRAGMENT_KEY);
        mealId = arguments.getInt("mealId");
        mealName = arguments.getString("mealName");
        double mealPrice = arguments.getDouble("mealPrice");

        TextView textViewMealName = (TextView) v.findViewById(R.id.text_view_meal_name);
        textViewMealName.setText(mealName);

        TextView price = (TextView) v.findViewById(R.id.text_view_meal_price);
        price.setText("" + mealPrice);

        RatingBar ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
        ratingBar.setNumStars(5);
        ratingBar.setRating(4);

        Button addToCart = (Button) v.findViewById(R.id.button_add_to_cart);
        final UserData userData = UserData.getInstance();

        if (userData.isAuthenticated()) {
            addToCart.setText("Add to cart");
            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = getString(R.string.service_add_to_cart);
                    JSONObject jsonId = new JSONObject();
                    try {
                        jsonId.put("id", Integer.toString(mealId));
                        jsonId.put("user_id", Integer.toString(userData.getId()));
                        Log.e("id jela za cart", " " + mealId);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String jsonIdString = jsonId.toString();
                    ListCartItems.getInstance().getCartItems(url, jsonIdString);

                    makeToast("Added " + mealName + " to cart.");

                    Intent i = new Intent(getActivity(), com.example.liquidsun.njamba.v3.MainActivity.class);
                    i.putExtra("toCart", 1);
                    startActivity(i);
                }
            });
        } else {
            addToCart.setText("Login to Add to Cart");
            //added line because of git
            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);

                }
            });
        }

        return v;
    }


    private void makeToast(final String message){

        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),
                                message,
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
