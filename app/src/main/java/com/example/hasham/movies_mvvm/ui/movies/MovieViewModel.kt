package com.example.hasham.movies_mvvm.ui.movies

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.example.hasham.movies_mvvm.ApplicationMain
import com.example.hasham.movies_mvvm.data.models.ApiResponse
import com.example.hasham.movies_mvvm.data.remote.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Developed by hasham on 11/27/17.
 */


class MovieViewModel(application: Application, private val navigator: MovieNavigator) : AndroidViewModel(application) {

    @Inject
    lateinit var retrofit: Retrofit
    @Inject
    lateinit var apiService: Repository.API


    init {

        (application as ApplicationMain).restComponent?.inject(this)
    }

    fun getMovies() {


        apiService.getVideos("12").enqueue(object : Callback<ApiResponse> {

            override fun onFailure(call: Call<ApiResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<ApiResponse>?, response: Response<ApiResponse>?) {
            }
        })


    }
}