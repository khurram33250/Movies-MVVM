package com.example.hasham.movies_mvvm.ui.movies

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.example.hasham.movies_mvvm.ApplicationMain
import com.example.hasham.movies_mvvm.data.models.DramaResponse
import com.example.hasham.movies_mvvm.data.models.MovieResponse
import com.example.hasham.movies_mvvm.data.remote.API
import com.example.hasham.movies_mvvm.data.repository.DramaRepository
import com.example.hasham.movies_mvvm.data.repository.MovieRepository
import javax.inject.Inject

/**
 * Developed by hasham on 11/27/17.
 */

class MovieViewModel(application: Application, private val navigator: MovieNavigator) : AndroidViewModel(application) {

    @Inject
    lateinit var apiService: API.Endpoints
    private var movieRepository: MovieRepository
    private var dramaRepository: DramaRepository

    private var apiMovieResponseObservable: LiveData<MovieResponse>
    private var apiDramaResponseObservable: LiveData<DramaResponse>

    var moviePage = MutableLiveData<Int>()
    var dramaPage = MutableLiveData<Int>()


    init {

        (application as ApplicationMain).restComponent?.inject(this)
        movieRepository = MovieRepository(apiService)
        dramaRepository = DramaRepository(apiService)

        apiMovieResponseObservable = Transformations.switchMap(moviePage, {
            movieRepository.getMovies(moviePage.value.toString())
        })

        apiDramaResponseObservable = Transformations.switchMap(dramaPage, {
            dramaRepository.getDramas(dramaPage.value.toString())
        })
    }

    fun getMoviesObservable(): LiveData<MovieResponse> = apiMovieResponseObservable

    fun getDramasObservable(): LiveData<DramaResponse> = apiDramaResponseObservable

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
}