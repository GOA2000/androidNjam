package com.example.liquidsun.njamba;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class NewMealActivity extends ActionBarActivity {

    private String mealName;
    private double mealPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meal);

        MealAdapter adapter = new MealAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        /////////
        Intent i = getIntent();
        mealName = i.getStringExtra("name");
        mealPrice = i.getDoubleExtra("price", 0);
    }


    private class MealAdapter extends FragmentStatePagerAdapter {

        public MealAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            Fragment show;

            if (position == 0) {
                show = new MainFragment();
                Bundle arguments = new Bundle();
                arguments.putInt(MainFragment.MAIN_FRAGMENT_KEY, position);
                arguments.putString("mealName", mealName);
                arguments.putDouble("mealPrice", mealPrice);

                show.setArguments(arguments);
            } else if (position == 1) {
                show = new DetailsFragment();
                Bundle arguments = new Bundle();
                arguments.putInt(DetailsFragment.DETAILS_FRAGMENT_KEY, position);

                show.setArguments(arguments);
            } else if (position == 2) {
                show = new ImagesFragment();
                Bundle arguments = new Bundle();
                arguments.putInt(ImagesFragment.IMAGES_FRAGMENT_KEY, position);

                show.setArguments(arguments);
            } else if (position == 3) {
                show = new AboutRestaurantFragment();
                Bundle arguments = new Bundle();
                arguments.putInt(AboutRestaurantFragment.ABOUT_RESTAURANT_FRAGMENT_KEY, position);

                show.setArguments(arguments);
            } else {
                show = new MealReviewsFragment();
                Bundle arguments = new Bundle();
                arguments.putInt(MealReviewsFragment.MEAL_REVIEWS_FRAGMENT_KEY, position);

                show.setArguments(arguments);
            }

            return show;
        }


        @Override
        public int getCount() {
            return 5;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Info";
            } else if (position == 1) {
                return "Details";
            } else if (position == 2) {
                return "Images";
            } else if (position == 3) {
                return "Restaurant";
            } else {
                return "Reviews";
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_meal, menu);
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
