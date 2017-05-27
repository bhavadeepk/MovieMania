package com.bhavadeep.moviemania;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by bhava on 5/16/2017.
 */

public class MovieAdapter extends BaseAdapter implements View.OnClickListener {
    Context mContext;
    List<Movie> movieList = new ArrayList<>();
    int[] samplePics = {R.drawable.a, R.drawable.b, R.drawable.c,R.drawable.d, R.drawable.e};
    public MovieAdapter(Context context) {
        mContext = context;
    }
    public void updateMovieAdapter(List<Movie> movies){
        movieList.clear();
        movieList.addAll(movies);
        this.notifyDataSetChanged();

    };

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Movie getItem(int position) {
        return movieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = (LayoutInflater.from(mContext)).inflate(R.layout.item_movie_main, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder)convertView.getTag();

        }

        Picasso.with(mContext).load(movieList.get(position).ImageUrl).centerCrop().fit().into(viewHolder.moviePoster);
        viewHolder.movieName.setText(movieList.get(position).movieName);

        return convertView ;
    }

    @Override
    public void onClick(View v) {

    }


    public static class ViewHolder{
        public ImageView moviePoster;
        public TextView movieName;

        public ViewHolder(View view){
            moviePoster = (ImageView)view.findViewById(R.id.movie_poster);
            movieName = (TextView)view.findViewById(R.id.movie_name);
        }


    };


}
