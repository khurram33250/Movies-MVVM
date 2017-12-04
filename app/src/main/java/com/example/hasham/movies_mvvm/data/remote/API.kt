package com.example.hasham.movies_mvvm.data.remote

import com.example.hasham.movies_mvvm.data.models.ApiResponse
import retrofit2.Call
import retrofit2.http.*
import java.util.HashMap

/**
 * Developed by hasham on 11/22/17.
 */

object API {

    const val ACTION_DISCOVER = "discover/movie"
    const val ACTION_RECOMMENDATION = "movie/{movie_id}/recommendations"
    const val ACTION_VIDEOS = "movie/{movie_id}/videos"

    interface Endpoints {

        @Headers("Content-Type: application/json")
        @GET(ACTION_DISCOVER)
        fun getMovies(@Query("page") page: String, @Query("sort_by")sortBy:String): Call<ApiResponse>

        @Headers("Content-Type: application/json")
        @GET(ACTION_RECOMMENDATION)
        fun getRelatedMovies(@Path("movie_id") movieId: String): Call<ApiResponse>

        @Headers("Content-Type: application/json")
        @GET(ACTION_VIDEOS)
        fun getVideos(@Path("movie_id") movieId: String): Call<ApiResponse>

    }
}
