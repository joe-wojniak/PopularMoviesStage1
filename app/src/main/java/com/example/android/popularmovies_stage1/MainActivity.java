package com.example.android.popularmovies_stage1;
/*
Popular Movies App Stage 1 (ud851 - AND)

Internet permissions and network state permissions are required in AndroidManifest.xml:
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

Project rubric:
https://review.udacity.com/#!/rubrics/66/view

Implementation Guide:
https://docs.google.com/document/d/1ZlN1fUsCSKuInLECcJkslIqvpKlP7jWL2TP9m6UiA6I/pub?embedded=true#h.cntdg23jy69n
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.image);
        Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").into(imageView);

        TextView textView = findViewById(R.id.text);
        textView.setText("Eureka!");
    }
    /*TODO: implement http request from TMDB
    In order to request popular movies you will want to request data from the /movie/popular and /movie/top_rated endpoints
    https://www.google.com/url?q=https://developers.themoviedb.org/3/discover/movie-discover&sa=D&ust=1541914498758000

    String apiKey = BuildConfig.ApiKey;

    Example request: http://api.themoviedb.org/3/movie/popular?api_key=[YOUR_API_KEY]
     */

    /*TODO: resolve movie poster path
    A note on resolving poster paths with themoviedb.org API
    You will notice that the API response provides a relative path to a movie poster image when you request the metadata for a specific movie.

    For example, the poster path return for Interstellar is “/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg”

    You will need to append a base path ahead of this relative path to build the complete url you will need to fetch the image using Picasso.

    It’s constructed using 3 parts:

    The base URL will look like: http://image.tmdb.org/t/p/.
    Then you will need a ‘size’, which will be one of the following: "w92", "w154", "w185", "w342", "w500", "w780", or "original". For most phones we recommend using “w185”.
    And finally the poster path returned by the query, in this case “/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg”

    Combining these three parts gives us a final url of http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
     */
}
