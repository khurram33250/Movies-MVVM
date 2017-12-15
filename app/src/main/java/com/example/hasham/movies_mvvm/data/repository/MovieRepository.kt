package com.example.hasham.movies_mvvm.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.hasham.movies_mvvm.data.models.MovieResponse
import com.example.hasham.movies_mvvm.data.remote.API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Developed by hasham on 11/27/17.
 */


class MovieRepository(private var movieService: API.Endpoints) {


    init {

        movieService = movieService
    }

    fun getMovies(page: String): LiveData<MovieResponse> {

        val data = MutableLiveData<MovieResponse>()

        movieService.getMovies(page, "popularity.desc").enqueue(object : Callback<MovieResponse> {

            override fun onFailure(call: Call<MovieResponse>?, t: Throwable?) {


            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {

                data.value = response.body()
            }
        })

        return data
    }

    fun getRelatedMovies(id: String): LiveData<MovieResponse> {

        val data = MutableLiveData<MovieResponse>()

        movieService.getRelatedMovies(id).enqueue(object : Callback<MovieResponse> {

            override fun onFailure(call: Call<MovieResponse>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {

                data.value = response.body()
                Log.e("response", data.value.toString())
            }
        })

        return data
    }

    fun getDramas(page: String): LiveData<MovieResponse> {

        val data = MutableLiveData<MovieResponse>()

        movieService.getDramas(page, "2015-09-15", "vote_count.gte", "18", "10").enqueue(object : Callback<MovieResponse> {

            override fun onFailure(call: Call<MovieResponse>?, t: Throwable?) {


            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {

                data.value = response.body()
            }
        })

        return data
    }
}