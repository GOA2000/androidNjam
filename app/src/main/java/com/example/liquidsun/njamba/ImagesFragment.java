package com.example.liquidsun.njamba;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImagesFragment extends Fragment {

    public ImagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_images, container, false);

        Bundle arguments = getArguments();
        String imgPath = arguments.getString("imgPath");
        Log.e("IMAGESFRAGMENT", imgPath);

        ImageView imageView = (ImageView) v.findViewById(R.id.image_view_meal);
        Picasso.with(getActivity().getBaseContext()).load(imgPath).into(imageView);

        return v;
    }


}
