package com.example.hasham.movies_mvvm.ui.movies

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.os.AsyncTask
import android.util.Log
import com.example.hasham.movies_mvvm.ApplicationMain
import com.example.hasham.movies_mvvm.data.models.MovieResponse
import com.example.hasham.movies_mvvm.data.remote.API
import com.example.hasham.movies_mvvm.data.repository.MovieRepository
import com.example.hasham.projectk.data.local.db.MovieDao
import javax.inject.Inject

/**
 * Developed by hasham on 11/27/17.
 */

class MovieViewModel(application: Application, private val navigator: MovieNavigator) : AndroidViewModel(application) {

    @Inject
    lateinit var apiService: API.Endpoints
    private var movieRepository: MovieRepository

    private var apiMovieResponseObservable: LiveData<MovieResponse>
    private var apiDramaResponseObservable: LiveData<MovieResponse>
    private val movieDao: MovieDao = getApplication<ApplicationMain>().getInstance().movieDao()

    var moviePage = MutableLiveData<Int>()
    var dramaPage = MutableLiveData<Int>()


    init {

        (application as ApplicationMain).restComponent?.inject(this)
        movieRepository = MovieRepository(apiService)

        apiMovieResponseObservable = Transformations.switchMap(moviePage, {
            movieRepository.getMovies(moviePage.value.toString())
        })

        apiDramaResponseObservable = Transformations.switchMap(dramaPage, {
            movieRepository.getDramas(dramaPage.value.toString())
        })

    }

    fun getMoviesObservable(): LiveData<MovieResponse> = apiMovieResponseObservable

    fun getDramasObservable(): LiveData<MovieResponse> = apiDramaResponseObservable

    fun requestMovies(nextPage: Int) {

        moviePage.value = nextPage
    }

    fun requestDramas(nextPage: Int) {
        dramaPage.value = nextPage
    }

    fun isLastMoviePage(moviePage: Int): Boolean {
        if (moviePage == 100) {
            return true
        }
        return false
    }

    fun getAllFavouriteMovies(): List<Movie> {
        return GetAllFavMoviesList().execute().get()
    }


    inner class GetAllFavMoviesList : AsyncTask<String, String, List<Movie>>() {

        override fun doInBackground(vararg params: String): List<Movie> {

            return movieDao.getAllMovie()
        }
    }
}