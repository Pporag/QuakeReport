package com.example.porag.quakereport;

public class Earthquake {

    // Holds the magnitude
    private Double mMagnitude;
    /** website URL od the earthquake */
    private String mUrl;

    // Holds the loaction
    private String mLocation ;

    // Date of the earthquake
    private String mDate ;
    // Time in miliseconds to date
    private long mTimeInMiiseconds ;




    public Earthquake (Double magnitude,String location, Long timeInMiliseconds, String url){
        mMagnitude = magnitude ;
        mUrl = url;
        mLocation = location ;
        mTimeInMiiseconds = timeInMiliseconds;
    }
    // Returns the magnitude of the earthquake
    public Double getMagnitude(){
        return mMagnitude;
    }

    // Returns the location of the earthquake
    public String getLocation (){
        return mLocation;
    }
    // Returns the date of the earthquake
    public long getmTimeInMiiseconds(){
        return mTimeInMiiseconds;
    }
    public String getUrl(){
        return mUrl;
    }
}
