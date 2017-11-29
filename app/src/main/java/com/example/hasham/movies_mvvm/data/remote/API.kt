package com.example.hasham.movies_mvvm.data.remote

import android.arch.lifecycle.LiveData
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
        fun getMovies(@QueryMap params: Map<String, String>): LiveData<Call<ApiResponse>>

        @Headers("Content-Type: application/json")
        @GET(ACTION_RECOMMENDATION)
        fun getRelatedMovies(@Path("movie_id") movieId: String): Call<ApiResponse>

        @Headers("Content-Type: application/json")
        @GET(ACTION_VIDEOS)
        fun getVideos(@Path("movie_id") movieId: String): Call<ApiResponse>

    }

    fun getMoviesParam(page: Int, maxDate: String, minDate: String): Map<String, String> {

        val params = HashMap<String, String>()
        params.put("page", page.toString())
        params.put("include_video", "false")
        params.put("include_adult", "false")
        params.put("sort_by", "popularity.desc")
        params.put("language", "en-US")
        params.put("primary_release_date.lte", maxDate)
        params.put("primary_release_date.gte", minDate)

        return params
    }
}
