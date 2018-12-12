package com.example.android.popularmovies_stage1.model;

import java.io.Serializable;

public class Movie implements Serializable {

    private String title;
    private String release_date;
    private String posterPath;
    private String vote_average;
    private String overview;
    private String popularity;

    public Movie(String title, String release_date, String posterPath, String vote_average,
                 String overview, String popularity) {

        this.title = title;
        this.release_date = release_date;
        this.posterPath = posterPath;
        this.vote_average = vote_average;
        this.overview = overview;
        this.popularity = popularity;

    }

    // Getter and Setter methods

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getVoteAverage() {
        return vote_average;
    }

    public void setVoteAverage(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }
}
