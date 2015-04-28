package com.example.liquidsun.njamba;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

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

        //TextView showText = (TextView) v.findViewById(R.id.text_view_show);
        //showText.setText("" + position);

        return v;
    }

}
