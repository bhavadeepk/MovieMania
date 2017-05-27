package com.bhavadeep.moviemania;

/**
 * Created by bhava on 5/21/2017.
 */

public class Movie{
    String movieName;
    String ImageUrl;
    String ID;
    String Rating;
    String ReleaseDate;
    String Plot;

    Movie(String Name, String id, String rating, String release_date, String plot, String imgURL){
        movieName = Name;
        Rating = rating;
        ReleaseDate = release_date;
        Plot = plot;
        movieName = Name;
        ImageUrl = imgURL;
        ID = id;
    }
}