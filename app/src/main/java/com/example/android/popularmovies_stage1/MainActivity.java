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

Example code adapted from these sources:
ud851-Exercises\Lesson03-Green-Recycler-View\T03.04-Exercise-WiringUpRecyclerView
StackOverflow: https://stackoverflow.com/questions/40587168/simple-android-grid-example-using-
recyclerview-with-gridlayoutmanager-like-the
and 101 apps.co.za: https://www.101apps.co.za/index.php/articles/gridview-tutorial-using-the-
picasso-library.html
https://www.101apps.co.za/index.php/articles/android-recyclerview-and-picasso-tutorial.html
https://www.101apps.co.za/index.php/ebooks/let-your-apps-take-a-giant-leap-a-tutorial.html*/

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private MovieRecyclerViewAdapter mAdapter;
    private RecyclerView mMoviePostersRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieOnClickListener = new MovieOnClickListener();
        
        mMoviePostersRecyclerView = findViewById(R.id.rvPosters);
        mMoviePostersRecyclerView.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this,3);
        mMoviePostersRecyclerView.setLayoutManager(mLayoutManager);

        articleList = getArticleData();

        mAdapter = new MovieRecyclerViewAdapter(this,articleList);
        mMoviePostersRecyclerView.setAdapter(mAdapter);

        webview = new WebView(this);

        setContentView(R.layout.progressbar);

        String url = getIntent().getStringExtra("articleUrl");
        if(url == null) {
            url = "http://www.101apps.co.za/";
        }
        webview.loadUrl(url);
        webview.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url){
                setContentView(webview);
            }
        }
    }

    @Override
    public void onClick (View v) {
        String selectedArticleUrl = getSelectedArticleUrl(v);
        showSelectedArticle(selectedArticleUrl);
    }

    private String getSelectedArticleUrl (View view) {
        int selectedItemPostion = mMoviePostersRecyclerView.getChildAdapterPosition(view);
        String url = MyArticleData.articles[selectedItemPosition][1];
        return url;
    }

    private void showSelectedArticle(String articleUrl){
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("articleUrl", articleUrl);
        startActivity(intent);
    }


    /*TODO: setup Gridlayout in MainActivity

    /*TODO: implement http request from TMDb
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
