package com.example.liquidsun.njamba;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class NewMainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);

        MainAdapter adapter = new MainAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        int toCart = getIntent().getIntExtra("toCart", 0);
        Log.e("INTENT TAG", "" + toCart);
        if (toCart == 1) {
            pager.setCurrentItem(2);
        } else {
            pager.setCurrentItem(1);
        }
    }


    private class MainAdapter extends FragmentStatePagerAdapter {

        public MainAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            Fragment show;

            if (position == 0) {
                show = new RestaurantsFragment();
            } else if (position == 1) {
                show = new MealsFragment();
            } else {
                show = new CartFragment();
            }

            return show;
        }


        @Override
        public int getCount() {
            return 3;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Restaurants";
            } else if (position == 1) {
                return "Meals";
            } else {
                return "Cart";
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_main, menu);
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

        if (id == R.id.action_bar_cart) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
