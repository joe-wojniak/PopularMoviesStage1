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
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.popularmovies_stage1.model.Movie;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>>,
        SharedPreferences.OnSharedPreferenceChangeListener {

    RecyclerView mMoviePostersRecyclerView;

    private static final String MOVIE_REQUEST_URL =
            "https://api.themoviedb.org/3/movie/";
    private static final int MOVIE_LOADER_ID = 1;
    private MovieRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviePostersRecyclerView = findViewById(R.id.rvPosters);
        mMoviePostersRecyclerView.setHasFixedSize(false);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 5);
        mMoviePostersRecyclerView.setLayoutManager(mGridLayoutManager);

        // Setup reference to SharedPreferences and register to be notified of changes
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

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
            Toast.makeText(this, "No internet connection found.\\nPlease try again later.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {

        String apiKey = BuildConfig.ApiKey;

        // Get preferences from Settings
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String movieSort = sharedPrefs.getString(getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_popular));

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
            Toast.makeText(this, "No internet connection found.\\nPlease try again later.",
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
            // no movies returned, display error message
            Toast.makeText(this, "No internet connection found.\\nPlease try again later.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        mAdapter.mMovieList.clear();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.settings_order_by_popular)) ||
                key.equals(getString(R.string.settings_order_by_key))) {
            mAdapter.mMovieList.clear();

            getLoaderManager().restartLoader(MOVIE_LOADER_ID, null, this);
        }
    }
}
