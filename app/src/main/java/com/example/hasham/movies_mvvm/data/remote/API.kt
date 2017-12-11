package com.example.hasham.movies_mvvm.data.remote

import com.example.hasham.movies_mvvm.data.models.DramaResponse
import com.example.hasham.movies_mvvm.data.models.MovieResponse
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
    const val ACTION_DRAMAS = "discover/movie"

    interface Endpoints {

        @Headers("Content-Type: application/json")
        @GET(ACTION_DISCOVER)
        fun getMovies(@Query("page") page: String, @Query("sort_by")sortBy:String): Call<MovieResponse>

        @Headers("Content-Type: application/json")
        @GET(ACTION_RECOMMENDATION)
        fun getRelatedMovies(@Path("movie_id") movieId: String): Call<MovieResponse>

        @Headers("Content-Type: application/json")
        @GET(ACTION_VIDEOS)
        fun getVideos(@Path("movie_id") movieId: String): Call<MovieResponse>

        @Headers("Content-Type: application/json")
        @GET(ACTION_DRAMAS)
        fun getDramas(@Query("page") page: String, @Query("sort_by")sortBy:String, @Query("with_genres")withGenres:String,
                      @Query("vote_count.gte")voteCount:String): Call<DramaResponse>

    }
}
