package com.example.android.popularmovies_stage1.utils;

public class MovieListService {

    public static String buildPosterURL(String posterSize, String posterPath) {

        // Build poster uri:
        final String baseUri = "http://image.tmdb.org/t/p/";
        String finishedPosterPath = baseUri + posterSize + posterPath;
        return finishedPosterPath;
    }
}
