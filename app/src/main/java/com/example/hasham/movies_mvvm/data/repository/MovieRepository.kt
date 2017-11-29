package com.example.hasham.movies_mvvm.data.repository

import com.example.hasham.movies_mvvm.data.remote.API
import javax.inject.Singleton

/**
 * Developed by hasham on 11/27/17.
 */

@Singleton
class MovieRepository(private var movieService: API.Endpoints) {


    init {

        movieService = movieService
    }



}