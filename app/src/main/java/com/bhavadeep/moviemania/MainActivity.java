package com.bhavadeep.moviemania;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    MovieAdapter movieAdapter;
    static final int PICK_CONTACT_REQUEST = 1;
    private static final String ARG_PARAM1 = "title";
    private static final String ARG_PARAM2 = "ratings";
    private static final String ARG_PARAM3 = "release_date";
    private static final String ARG_PARAM4 = "plot";
    private static final String ARG_PARAM5 = "id";
    private static final String SORT_POPULAR = "popular";
    private static final String SORT_TOPRATED = "top_rated";
    private static final String SORT_NOWPLAYING = "now_playing";
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie";
    private static final String API_KEY = "api_key";
    private static final String LANG = "language";
    private static final String REGION = "region";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView mainGrid = (GridView)findViewById(R.id.main_gridview);
        movieAdapter = new MovieAdapter(this);
        mainGrid.setAdapter(movieAdapter);
        mainGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Movie movieClicked = (Movie) parent.getAdapter().getItem(position);
                Bundle movieBundle = new Bundle();
                movieBundle.putString(ARG_PARAM1, movieClicked.movieName);
                movieBundle.putString(ARG_PARAM2, movieClicked.Rating);
                movieBundle.putString(ARG_PARAM3, movieClicked.ReleaseDate);
                movieBundle.putString(ARG_PARAM4, movieClicked.Plot);
                movieBundle.putString(ARG_PARAM5, movieClicked.ID);
                Intent detailsIntent = new Intent(MainActivity.this, MovieDetailsActivity.class);
                detailsIntent.putExtra("movie_bundle", movieBundle);
                startActivity(detailsIntent);

            }
        });



    }
    public void UpdateMovies(){
        FetchMovieData fetchMovieData = new FetchMovieData(movieAdapter);
        SharedPreferences sortPref = PreferenceManager.getDefaultSharedPreferences(this);
        String sortPreference = sortPref.getString("sort_key", getString(R.string.popular));

        Uri uri = Uri.parse(BASE_URL);
        Uri.Builder builder= uri.buildUpon()
                .appendPath(sortPreference)
                .appendQueryParameter(LANG, "en-US")
                .appendQueryParameter(REGION, "us")
                .appendQueryParameter(API_KEY, getString(R.string.theMoviDB_id));

        fetchMovieData.execute(builder.build().toString());

    }

    @Override
    protected void onStart() {
        super.onStart();
        UpdateMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu    , menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settings:
            {


                startActivity(new Intent(this, SettingsActivity.class));




                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
