package com.example.liquidsun.njamba;

/**
 * Created by Liquid Sun on 15/04/2015.
 */
public class Restaurant {



    private int mId;
    private String mName;
    private String  mLocation;
    private double mMinOrder;
    private String mImgLocation;

    public Restaurant(int id, String name) {
        mId = id;
        mName = name;
       // mContent = content;
    }

    public Restaurant(int id, String name, String location) {
        mId = id;
        mName = name;
        mLocation =location;
     //   mImgLocation=imgLocation;

    }

    public String toString(){
        return mName +" \n "+ "Location " + mLocation;
    }


    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getTitle() {
        return mName;
    }

    public void setTitle(String title) {
        mName = title;
    }

    public String getName() {
        return mName;
    }

    public String getImgLocation() {
        return mImgLocation;
    }

    public void setImgLocation(String imgLocation) {
        mImgLocation = imgLocation;
    }
}


