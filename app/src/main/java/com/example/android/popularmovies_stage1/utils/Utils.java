package com.example.android.popularmovies_stage1.utils;

import android.util.Log;

import com.example.android.popularmovies_stage1.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    private static final String LOG_TAG = Utils.class.getSimpleName();

    private static String KEY_RESULTS = "results";
    private static String KEY_TITLE = "title";
    private static String KEY_RELEASE = "release_date";
    private static String KEY_POSTER = "poster_path";
    private static String KEY_VOTE = "vote_average";
    private static String KEY_OVERVIEW = "overview";
    private static String KEY_POP = "popularity";

    private Utils() {
    }

    /**
     * Query the TMDb api and return {@link Movie} objects.
     */
    public static List<Movie> fetchMovieData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        List<Movie> movies = extractFeatureFromJson(jsonResponse);

        return movies;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results. ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<Movie> extractFeatureFromJson(String json) {

        List<Movie> movieList = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(json);
            JSONArray resultsArray = baseJsonResponse.optJSONArray(KEY_RESULTS);

            // looping through the page results
            for (int i = 0; i < resultsArray.length(); i++) {
                // Extract out the first result (which is a movie)
                JSONObject firstResult = resultsArray.getJSONObject(i);

                // Extract out the title, release date, poster_path, vote average,
                // plot synopsis (overview), and popularity rating
                String title = firstResult.optString(KEY_TITLE);
                String releaseDate = firstResult.optString(KEY_RELEASE);
                String poster_path = firstResult.optString(KEY_POSTER);
                String voteAverage = firstResult.optString(KEY_VOTE);
                String overview = firstResult.optString(KEY_OVERVIEW);
                String popularity = firstResult.optString(KEY_POP);

                // Make new movie object using data attributes from json
                // Add movie object to list of movies
                Movie movie = new Movie(title, releaseDate, poster_path, voteAverage, overview, popularity);
                movieList.add(movie);
            }

        } catch (final JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the JSON results ", e);
        }
        return movieList;
    }
}
