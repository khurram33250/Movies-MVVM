package com.example.hasham.projectk.data.local.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.hasham.movies_mvvm.data.models.Movie

/**
 * Created by Khurram on 21-Nov-17.
 */
@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie")
    fun getMovie(): LiveData<List<Movie>>

    @Query("SELECT * FROM Movie")
    fun getAllMovie(): List<Movie>

    @Query("SELECT * FROM Movie WHERE id = :Id")
    fun getMovieById(Id: String): Movie?

    @Query("SELECT * FROM Movie WHERE title = :title")
    fun getMovieByTitle(title: String): Movie?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Update
    fun updateMovie(movie: Movie): Int

    @Query("DELETE FROM Movie WHERE title = :Title ")
    fun deleteMovieByTitle(Title: String)
}