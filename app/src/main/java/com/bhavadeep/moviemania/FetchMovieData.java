package com.bhavadeep.moviemania;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

import static com.bhavadeep.moviemania.R.drawable.e;

/**
 * Created by bhava on 5/19/2017.
 */

class FetchMovieData extends AsyncTask<String, Integer, List<Movie>> {

    private List<Movie> nowPlayingMovies = new ArrayList<Movie>();
    Context context;
    private MovieAdapter movieAdapter;
    final String SORT_POPULAR = "popular";
    final String SORT_TOPRATED = "top_rated";
    final String SORT_NOWPLAYING = "now_playing";
    final String BASE_URL = "https://api.themoviedb.org/3/movie/popular?";
    final String API_KEY = "api_key";
    final String LANG = "language";
    private static final String IMAGE_BASEURL = "https://image.tmdb.org/t/p/w1920";
    final String REGION = "region";
    public FetchMovieData(MovieAdapter movieadapter){
        movieAdapter = movieadapter;

    }

    @Override
    protected List<Movie> doInBackground(String...urls) {

        int pages = 0;
        HttpURLConnection urlConnection = null;
       try {
           URL nowplayingUrl = new URL(urls[0]);
            urlConnection = (HttpURLConnection) nowplayingUrl.openConnection();
           urlConnection.setRequestMethod("GET");
           urlConnection.connect();
           InputStream nowPlayingInputStream = urlConnection.getInputStream();
           JsonReader reader = new JsonReader(new InputStreamReader(nowPlayingInputStream));
           reader.beginObject();
           while (reader.hasNext())
           {
               switch (reader.nextName()) {
                   case "results" :
                   {
                       reader.beginArray();
                       while(reader.hasNext()){
                           reader.beginObject();
                           String movieName = null;
                           String posterUrl = null;
                           String id = null;
                           String rating = null;
                           String release_date = null;
                           String plot = null;
                           while(reader.hasNext()){

                               String key = reader.nextName();
                               switch ( key ){
                                   case  "original_title":{

                                                       movieName = reader.nextString();
                                                        break;

                                                   }
                                   case "id":{

                                                           id = reader.nextString();
                                       break;

                                                       }
                                   case "poster_path":{
                                                       posterUrl = IMAGE_BASEURL + reader.nextString();
                                       break;
                                                   }
                                   case "vote_average":{
                                                       rating = reader.nextString();
                                       break;
                                   }
                                   case "release_date":{
                                                       release_date = reader.nextString();
                                       break;
                                   }
                                   case "overview":{
                                                        plot = reader.nextString();
                                       break;
                                   }
                               default:
                                   reader.skipValue();
                                   break;
                               }
                           }

                           Movie movie = new Movie(movieName, id, rating, release_date, plot, posterUrl);
                           nowPlayingMovies.add(movie);
                           reader.endObject();
                       }
                       reader.endArray();
                   }break;
                   case "total_pages": {
                       pages = reader.nextInt();
                       break;
                   }
                   default:
                       reader.skipValue();
               }
           }
           reader.endObject();
           reader.close();

       }
       catch (Exception e)
       {
           e.printStackTrace();
       }

        return nowPlayingMovies;
    }

    @Override
    protected void onPostExecute(List<Movie> list) {
        super.onPostExecute(list);
        movieAdapter.updateMovieAdapter(list);
    }
}
