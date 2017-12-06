package com.example.hasham.movies_mvvm.data.local.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.hasham.movies_mvvm.data.models.Movie

/**
 * Created by Waqas on 06-Dec-17.
 */
@Dao
interface FavMoviesDao {
    @Query("SELECT * FROM Movie")
    fun getMovie(): LiveData<List<Movie>>

    @Query("SELECT * FROM Movie WHERE bid = :Id")
    fun getMovieById(Id: String): Movie?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavMovie(movie: Movie)

    @Update
    fun updateMovie(movie: Movie): Int

    @Query("DELETE FROM Movie")
    fun deleteMovie()
}