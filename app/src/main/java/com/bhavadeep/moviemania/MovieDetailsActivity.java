package com.bhavadeep.moviemania;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MovieDetailsActivity extends AppCompatActivity {
    private static final String ARG_PARAM1 = "title";
    private static final String ARG_PARAM2 = "ratings";
    private static final String ARG_PARAM3 = "release_date";
    private static final String ARG_PARAM4 = "plot";
    private static final String ARG_PARAM5 = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Bundle movieSelected_bundle = getIntent().getExtras().getBundle("movie_bundle");
        MovieDetailsFragment movieDetailsFragment = MovieDetailsFragment.newInstance(movieSelected_bundle.getString(ARG_PARAM1), movieSelected_bundle.getString(ARG_PARAM2), movieSelected_bundle.getString(ARG_PARAM3),
                                            movieSelected_bundle.getString(ARG_PARAM4), movieSelected_bundle.getString(ARG_PARAM5));

        getSupportFragmentManager().beginTransaction().add(R.id.movie_details_container,movieDetailsFragment).commit();

    }

}
