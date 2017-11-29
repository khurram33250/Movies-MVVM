package com.example.hasham.movies_mvvm.data.remote

import com.example.hasham.movies_mvvm.data.models.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

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
        fun getMovies(@Path("page") page: String, @Path("include_video") includeVideo: String,
                      @Path("include_adult") includeAdult: String, @Path("sort_by") sortBy: String, @Path("language")
                      language: String, @Path("primary_release_date.lte") startReleaseDate: String, @Path("primary_release_date.gte")
                      endReleaseDate: String): Call<ApiResponse>

        @Headers("Content-Type: application/json")
        @GET(ACTION_RECOMMENDATION)
        fun getRelatedMovies(@Path("movie_id") movieId: String): Call<ApiResponse>

        @Headers("Content-Type: application/json")
        @GET(ACTION_VIDEOS)
        fun getVideos(@Path("movie_id") movieId: String): Call<ApiResponse>

    }
}
