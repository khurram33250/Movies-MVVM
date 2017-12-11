package com.example.hasham.movies_mvvm.ui.moviesDetail

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.example.hasham.movies_mvvm.ApplicationMain
import com.example.hasham.movies_mvvm.data.models.Movie
import com.example.hasham.movies_mvvm.data.models.MovieResponse
import com.example.hasham.movies_mvvm.data.remote.API
import com.example.hasham.movies_mvvm.data.repository.MovieRepository
import javax.inject.Inject

/**
 * Created by Khurram on 05-Dec-17.
 */
class MovieDetailViewModel(application: Application, private val navigator: MovieDetailNavigator) : AndroidViewModel(application) {

    @Inject
    lateinit var apiService: API.Endpoints
    private var repository: MovieRepository

    private var apiResponseObservable: LiveData<MovieResponse>

    var id = MutableLiveData<Int>()

    init {

        (application as ApplicationMain).restComponent?.inject(this)
        repository = MovieRepository(apiService)

        //    apiResponseObservable = repository.getRelatedMovies(id.toString())

        apiResponseObservable = Transformations.switchMap(id, {
            repository.getRelatedMovies(id.value.toString())
        })
    }

    fun getRelatedMoviesObservable(): LiveData<MovieResponse> = apiResponseObservable

    fun requestRelatedMovies(pageId: Int) {

        id.value = pageId
    }

    fun addToFavourites(movie: Movie) {

    }
}