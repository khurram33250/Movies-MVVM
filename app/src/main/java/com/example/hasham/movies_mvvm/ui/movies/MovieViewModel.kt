package com.example.hasham.movies_mvvm.ui.movies

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.util.Log
import com.example.hasham.movies_mvvm.ApplicationMain
import com.example.hasham.movies_mvvm.data.models.ApiResponse
import com.example.hasham.movies_mvvm.data.remote.API
import com.example.hasham.movies_mvvm.data.repository.MovieRepository
import javax.inject.Inject

/**
 * Developed by hasham on 11/27/17.
 */

class MovieViewModel(application: Application, private val navigator: MovieNavigator) : AndroidViewModel(application) {

    @Inject
    lateinit var apiService: API.Endpoints
    private var repository: MovieRepository

    private var apiResponseObservable: LiveData<ApiResponse>

    var page = MutableLiveData<Int>()

    init {

        (application as ApplicationMain).restComponent?.inject(this)
        repository = MovieRepository(apiService)

        apiResponseObservable = Transformations.switchMap(page, {
            repository.getMovies(page.value.toString())
        })
    }

    fun getMoviesObservable(): LiveData<ApiResponse> = apiResponseObservable

    fun requestMovies(nextPage: Int) {

        page.value = nextPage
    }

    fun isLastPage(page: Int): Boolean {
        if (page == 100) {
            return true
        }
        return false
    }
}