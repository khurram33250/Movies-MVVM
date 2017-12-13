package com.example.hasham.movies_mvvm.ui.favouriteMovies

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.example.hasham.movies_mvvm.ApplicationMain
import com.example.hasham.movies_mvvm.data.models.Movie
import com.example.hasham.projectk.data.local.db.MovieDao


class FavouriteMoviesViewModel(application: Application, private val navigator: FavouriteMoviesNavigator) : AndroidViewModel(application) {

    private val movieDao: MovieDao = getApplication<ApplicationMain>().getInstance().movieDao()

    fun getFavouriteMovies(): LiveData<List<Movie>> {
        return GetFavMoviesList().execute().get()
    }

    inner class GetFavMoviesList : AsyncTask<String, String, LiveData<List<Movie>>>() {

        override fun doInBackground(vararg params: String): LiveData<List<Movie>> {

            return movieDao.getMovie()
        }
    }

}