package com.example.hasham.projectk.data.local.db

import android.arch.persistence.room.*
import com.example.hasham.movies_mvvm.data.models.Movie

/**
 * Created by Khurram on 21-Nov-17.
 */
@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie")
    fun getMovie(): List<Movie>?

    @Query("SELECT * FROM Movie WHERE bid = :Id")
    fun getMovieById(Id: String): Movie?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Update
    fun updateMovie(movie: Movie): Int

    @Query("DELETE FROM Movie")
    fun deleteMovie()
}