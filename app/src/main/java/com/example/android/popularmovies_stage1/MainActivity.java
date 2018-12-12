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

Example code adapted from:
ud851-Exercises\Lesson03-Green-Recycler-View\T03.04-Exercise-WiringUpRecyclerView
https://www.codingdemos.com/android-gridlayout-example-recyclerview/
Sections of code functionality modified from NewsApp Stage 2 (ABND Project 6) and
https://medium.com/@sanjeevy133/an-idiots-guide-to-android-asynctaskloader-76f8bfb0a0c0
implementing background thread call to TMDb api.
*/

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.popularmovies_stage1.model.Movie;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

    RecyclerView mMoviePostersRecyclerView;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String MOVIE_REQUEST_URL =
            "https://api.themoviedb.org/3/movie/";
    private static final int MOVIE_LOADER_ID = 1;
    private MovieRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOG_TAG,"start Main Activity onCreate method");

        mMoviePostersRecyclerView = findViewById(R.id.rvPosters);
        mMoviePostersRecyclerView.setHasFixedSize(false);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 5);
        mMoviePostersRecyclerView.setLayoutManager(mGridLayoutManager);

        // TODO Remove static json:
        //String json = getResources().getString(R.string.json);
        //List<Movie> movieList = Utils.extractFeatureFromJson(json);

        //mAdapter = new MovieRecyclerViewAdapter(this, new ArrayList<Movie>());
        //mMoviePostersRecyclerView.setAdapter(mAdapter);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getSupportLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(MOVIE_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // Update empty state with no connection error message
            Toast.makeText(this,"No internet connection found.\\nPlease try again later.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {

        String apiKey = BuildConfig.ApiKey;
        String movieSort = "top_rated";

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // Build Uri

            Uri baseUri = Uri.parse(MOVIE_REQUEST_URL);
            Uri.Builder uriBuilder = baseUri.buildUpon();

            uriBuilder.appendEncodedPath(movieSort);
            uriBuilder.appendQueryParameter("api_key", apiKey);
            uriBuilder.appendQueryParameter("language", "en-US");
            uriBuilder.appendQueryParameter("page", "1");

            return new MovieLoader(this, uriBuilder.toString());
        } else {
            // Otherwise, display error
            // Update empty state with no connection error message
            Toast.makeText(this,"No internet connection found.\\nPlease try again later.",
                    Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movieList) {
        if (movieList != null && !movieList.isEmpty()) {
            mAdapter = new MovieRecyclerViewAdapter(this, movieList);
            mMoviePostersRecyclerView.setAdapter(mAdapter);
            Toast.makeText(this, "Movies Loaded", Toast.LENGTH_SHORT).show();
        } else {
            // no news returned, display error message
            Toast.makeText(this,"No internet connection found.\\nPlease try again later.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mostPopular) {
            Toast.makeText(this, "Most Popular", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.topRated) {
            Toast.makeText(this, "Top Rated", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
