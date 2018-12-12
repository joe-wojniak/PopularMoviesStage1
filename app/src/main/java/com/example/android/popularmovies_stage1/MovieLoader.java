package com.example.android.popularmovies_stage1;

/* Sections of code functionality modified from NewsApp Stage 2 (ABND Project 6) and
https://medium.com/@sanjeevy133/an-idiots-guide-to-android-asynctaskloader-76f8bfb0a0c0
implementing background thread call to TMDb api. */

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.popularmovies_stage1.model.Movie;
import com.example.android.popularmovies_stage1.utils.Utils;

import java.util.List;

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    private String mUrl;

    public MovieLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list of movies.
        List<Movie> movieList = Utils.fetchMovieData(mUrl);
        return movieList;
    }

}
