package com.example.android.popularmovies.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM favorites")
    List<Movie> loadAllMovies();

    @Insert
    void insertMovie(Movie movieEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Movie movieEntry);

    @Delete
    void deleteMovie(Movie movieEntry);
}
