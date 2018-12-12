package com.example.android.popularmovies_stage1;

/* Example code modified from these sources:
ud851-Exercises\Lesson03-Green-Recycler-View\T03.04-Exercise-WiringUpRecyclerView
https://www.codingdemos.com/android-gridlayout-example-recyclerview/
*/

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies_stage1.model.Movie;
import com.example.android.popularmovies_stage1.utils.MovieListService;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder> {

    Context mContext;
    List<Movie> mMovieList;
    String posterPath;

    private static final String TAG = MovieRecyclerViewAdapter.class.getSimpleName();

    public MovieRecyclerViewAdapter(Context mContext, List<Movie> mMovieList) {
        this.mContext = mContext;
        this.mMovieList = mMovieList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,
                parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        final Movie m = mMovieList.get(position);

        Log.d(TAG,"Posterpath: "+m.getPosterPath());
        posterPath = MovieListService.buildPosterURL("w185", m.getPosterPath());

        Picasso.with(mContext)
                .load(posterPath)
                .into(holder.posterImage);

        holder.posterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(mContext, DetailActivity.class);
                mIntent.putExtra("movieTitle", m.getTitle());
                mIntent.putExtra("movieReleaseDate", m.getRelease_date());
                mIntent.putExtra("movieVoteAverage", m.getVoteAverage());
                mIntent.putExtra("movieOverview", m.getOverview());
                mIntent.putExtra("moviePosterPath", m.getPosterPath());
                mContext.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImage;

        public MovieViewHolder(View itemView) {
            super(itemView);
            posterImage = itemView.findViewById(R.id.rvItemImage);
        }
    }
}