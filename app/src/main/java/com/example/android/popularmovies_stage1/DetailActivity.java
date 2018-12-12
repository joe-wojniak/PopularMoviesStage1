package com.example.android.popularmovies_stage1;

/*Example code adapted from:
        ud851-Exercises\Lesson03-Green-Recycler-View\T03.04-Exercise-WiringUpRecyclerView
        https://www.codingdemos.com/android-gridlayout-example-recyclerview/*/

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies_stage1.utils.MovieListService;
import com.squareup.picasso.Picasso;

public class DetailActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Context mContext = null;
        String posterPath = null;

        ImageView mMovie = findViewById(R.id.detailImageView);
        TextView mTitle = findViewById(R.id.detailTitle);
        TextView mReleaseDate = findViewById(R.id.detailReleaseDate);
        TextView mVoteAverage = findViewById(R.id.detailVoteAverage);
        TextView mOverview = findViewById(R.id.detailOverview);

        Bundle mBundle = getIntent().getExtras();
        if(mBundle!=null){
            mTitle.setText(mBundle.getString("movieTitle"));
            mReleaseDate.setText(mBundle.getString("movieReleaseDate"));
            mVoteAverage.setText(mBundle.getString("movieVoteAverage"));
            mOverview.setText(mBundle.getString("movieOverview"));
            posterPath = mBundle.getString("moviePosterPath");
        }
        
        String mMoviePosterPath = MovieListService.buildPosterURL( "w500", posterPath);
        
        Picasso.with(mContext)
                .load(mMoviePosterPath)
                .into(mMovie);
        
    }
}
