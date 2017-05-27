package com.bhavadeep.moviemania;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link MovieDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "title";
    private static final String ARG_PARAM2 = "ratings";
    private static final String ARG_PARAM3 = "release_date";
    private static final String ARG_PARAM4 = "plot";
    private static final String ARG_PARAM5 = "id";

    // TODO: Rename and change types of parameters
    private String movie_title;
    private String movie_ratings;
    private String movie_release_date;
    private String movie_plot;
    private String movie_id;
    private TextView mTitle_textView;
    private TextView mRatings_textView;
    private TextView mReleaseDate_textView;
    private TextView mPlot_textView;
    private SliderLayout sliderLayout;
    private static final String default_start = "https://api.themoviedb.org/3/movie/";
    private static final String IMAGES ="images";
    private static final String API_KEY = "api_key";
    private static final String LANG = "language";
    private static final String IMAGE_BASEURL = "https://image.tmdb.org/t/p/w1920";
    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MovieDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailsFragment newInstance(String title, String rating, String release_date, String plot, String id) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, title);
        args.putString(ARG_PARAM2, rating);
        args.putString(ARG_PARAM3, release_date);
        args.putString(ARG_PARAM4, plot);
        args.putString(ARG_PARAM5, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitle_textView.setText(movie_title);
        mPlot_textView.setText(movie_plot);
        mReleaseDate_textView.setText(movie_release_date);
        mRatings_textView.setText(movie_ratings);


        Uri.Builder builder = Uri.parse(default_start).buildUpon().appendPath(movie_id).appendPath(IMAGES)
                .appendQueryParameter(API_KEY, getString(R.string.theMoviDB_id)).appendQueryParameter(LANG, "en");
        FetchpostersTask fetchTask = new FetchpostersTask();
        fetchTask.execute(builder.build().toString());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        mTitle_textView = (TextView)rootView.findViewById(R.id.title_name);
        mRatings_textView = (TextView)rootView.findViewById(R.id.rating);
        mReleaseDate_textView = (TextView)rootView.findViewById(R.id.release_date);
        mPlot_textView = (TextView)rootView.findViewById(R.id.overview);
        sliderLayout = (SliderLayout)rootView.findViewById(R.id.slider);
        if (getArguments() != null) {
            movie_title = getArguments().getString(ARG_PARAM1);
            movie_ratings = getArguments().getString(ARG_PARAM2);
            movie_release_date = getArguments().getString(ARG_PARAM3);
            movie_plot = getArguments().getString(ARG_PARAM4);
            movie_id = getArguments().getString(ARG_PARAM5);
        }
        return  rootView;


    }



    public class FetchpostersTask extends AsyncTask<String, Integer, List<String>>{
        List<String> result = new ArrayList<>();
        @Override
        protected List<String> doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            try {
                URL imageURL = new URL(params[0]);
                urlConnection = (HttpURLConnection) imageURL.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream nowPlayingInputStream = urlConnection.getInputStream();
                JsonReader reader = new JsonReader(new InputStreamReader(nowPlayingInputStream));
                reader.beginObject();
                while (reader.hasNext()){
                   if (reader.nextName().equals("posters")) {
                            reader.beginArray();
                       int count = 0;
                            while (reader.hasNext()) {
                                reader.beginObject();
                                String imageEtxn = null;

                                while(reader.hasNext())
                                {
                                    if(reader.nextName().equals("file_path")){
                                        imageEtxn = IMAGE_BASEURL + reader.nextString();
                                    }
                                    else
                                        reader.skipValue();
                                }
                                    result.add(imageEtxn);
                                    reader.endObject();
                            }
                            reader.endArray();
                    }else
                        reader.skipValue();


                }reader.endObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            for(int n=0;n<6;n++){
                TextSliderView textSliderView = new TextSliderView(getContext());
                textSliderView.image(strings.get(new Random().nextInt(strings.size()))).setScaleType(BaseSliderView.ScaleType.Fit);
                sliderLayout.addSlider(textSliderView);
            }

        }

    }
    @Override
    public void onStop() {
        sliderLayout.stopAutoCycle();
        super.onStop();
    }


}
