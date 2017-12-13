package com.example.hasham.movies_mvvm.ui.moviesDetail

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.os.AsyncTask
import android.util.Log
import com.example.hasham.movies_mvvm.ApplicationMain
import com.example.hasham.movies_mvvm.data.models.ApiResponse
import com.example.hasham.movies_mvvm.data.models.Movie
import com.example.hasham.movies_mvvm.data.remote.API
import com.example.hasham.movies_mvvm.data.repository.MovieRepository
import com.example.hasham.projectk.data.local.db.MovieDao
import javax.inject.Inject

/**
 * Created by Khurram on 05-Dec-17.
 */
class MovieDetailViewModel(application: Application, private val navigator: MovieDetailNavigator) : AndroidViewModel(application) {

    @Inject
    lateinit var apiService: API.Endpoints
    private var repository: MovieRepository

    private val movieDao: MovieDao = getApplication<ApplicationMain>().getInstance().movieDao()

    private var apiResponseObservable: LiveData<ApiResponse>

    var id = MutableLiveData<Int>()

    init {

        (application as ApplicationMain).restComponent?.inject(this)
        repository = MovieRepository(apiService)


        apiResponseObservable = repository.getRelatedMovies(id.value.toString())
    }

    fun getRelatedMoviesObservable(): LiveData<ApiResponse> = apiResponseObservable

    fun requestRelatedMovies(pageId: Int) {

        id.value = pageId
    }

    fun addToFavourites(movie: Movie) {
        InsertFavMovie().execute(movie)
        Log.e("name", movie.toString())
    }

    fun deleteItem(movie: Movie) {
        deleteFavMovie(movie).execute(movie)
    }

    inner class InsertFavMovie : AsyncTask<Movie, String, String>() {

        override fun doInBackground(vararg params: Movie): String? {

            movieDao.insertMovie(params[0])
            return null
        }
    }

    inner class deleteFavMovie(movie: Movie) : AsyncTask<Movie, String, String>() {

        override fun doInBackground(vararg params: Movie): String? {

            movieDao.deleteMovie(String())
            return null
        }
    }
}