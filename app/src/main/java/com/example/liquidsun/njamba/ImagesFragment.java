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
public class ImagesFragment extends Fragment {


    public static final String IMAGES_FRAGMENT_KEY = "ba.bitcamp.neldin.images_fragment_key";

    public ImagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_images, container, false);

        Bundle arguments = getArguments();
        int position = arguments.getInt(IMAGES_FRAGMENT_KEY);

        //TextView showText = (TextView) v.findViewById(R.id.text_view_show);
        //showText.setText("" + position);

        return v;
    }


}
