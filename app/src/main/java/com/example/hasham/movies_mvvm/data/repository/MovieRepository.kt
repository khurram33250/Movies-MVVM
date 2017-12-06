package com.example.hasham.movies_mvvm.data.repository

import android.arch.lifecycle.LiveData
import com.example.hasham.movies_mvvm.data.models.ApiResponse
import com.example.hasham.movies_mvvm.data.remote.API
import retrofit2.Call
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import retrofit2.Callback
import retrofit2.Response


/**
 * Developed by hasham on 11/27/17.
 */


class MovieRepository(private var movieService: API.Endpoints) {


    init {

        movieService = movieService
    }

    fun getMovies(page: String): LiveData<ApiResponse> {

        val data = MutableLiveData<ApiResponse>()

        movieService.getMovies(page,"popularity.desc").enqueue(object : Callback<ApiResponse> {

            override fun onFailure(call: Call<ApiResponse>?, t: Throwable?) {


            }

            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {

                data.value = response.body()
            }
        })

        return data
    }

    fun getRelatedMovies(id: String): LiveData<ApiResponse> {

        val data = MutableLiveData<ApiResponse>()

        movieService.getRelatedMovies(id).enqueue(object : Callback<ApiResponse> {

            override fun onFailure(call: Call<ApiResponse>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {

                data.value = response.body()
                Log.e("response", data.value.toString())
            }
        })

        return data
    }
}