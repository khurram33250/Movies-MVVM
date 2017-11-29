package com.example.hasham.movies_mvvm.ui.movies

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.example.hasham.movies_mvvm.ApplicationMain
import com.example.hasham.movies_mvvm.data.remote.API
import com.example.hasham.movies_mvvm.data.repository.MovieRepository
import javax.inject.Inject
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.hasham.movies_mvvm.data.models.ApiResponse
import com.example.hasham.movies_mvvm.data.models.Movie


/**
 * Developed by hasham on 11/27/17.
 */


class MovieViewModel(application: Application, private val navigator: MovieNavigator) : AndroidViewModel(application) {

    @Inject
    lateinit var apiService: API.Endpoints
    private var repository: MovieRepository

    private var apiResponseObservable: LiveData<ApiResponse>

    init {

        (application as ApplicationMain).restComponent?.inject(this)
        repository = MovieRepository(apiService)



        apiResponseObservable = repository.getMovies("1")
    }

    fun getMoviesObservable(): LiveData<ApiResponse> = apiResponseObservable

}