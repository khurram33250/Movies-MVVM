package com.example.hasham.movies_mvvm.ui.favouriteMovies

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.example.hasham.movies_mvvm.ApplicationMain
import com.example.hasham.movies_mvvm.data.local.db.FavMoviesDao
import com.example.hasham.movies_mvvm.data.models.Movie

import com.example.hasham.projectk.data.local.db.AppDatabase

/**
 * Created by Waqas on 06-Dec-17.
 */
class FavouriteMoviesViewModel(application: Application, private val navigator: FavouriteMoviesNavigator) : AndroidViewModel(application) {
    private lateinit var appDatabase: AppDatabase
    private lateinit var favMoviesDao: FavMoviesDao

    init {
        favMoviesDao = getApplication<ApplicationMain>().getInstance().favMoviesDao()

    }

    inner class GetFavMoviesList : AsyncTask<String, String, LiveData<List<Movie>>>() {


        override fun doInBackground(vararg params: String): LiveData<List<Movie>> {

            return favMoviesDao.getMovie()
        }
}}