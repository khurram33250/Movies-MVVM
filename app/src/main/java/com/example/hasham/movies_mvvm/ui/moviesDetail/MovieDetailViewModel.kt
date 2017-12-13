package com.example.hasham.movies_mvvm.ui.moviesDetail

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.os.AsyncTask
import android.support.design.widget.FloatingActionButton
import com.example.hasham.movies_mvvm.ApplicationMain
import com.example.hasham.movies_mvvm.R
import com.example.hasham.movies_mvvm.data.models.Movie
import com.example.hasham.movies_mvvm.data.models.MovieResponse
import com.example.hasham.movies_mvvm.data.remote.API
import com.example.hasham.movies_mvvm.data.repository.MovieRepository
import com.example.hasham.projectk.data.local.db.MovieDao
import javax.inject.Inject
import android.databinding.ObservableField


/**
 * Created by Khurram on 05-Dec-17.
 */
class MovieDetailViewModel(application: Application, private val navigator: MovieDetailNavigator) : AndroidViewModel(application) {

    @Inject
    lateinit var apiService: API.Endpoints
    private var repository: MovieRepository

    private var apiResponseObservable: LiveData<MovieResponse>

    private val movieDao: MovieDao = getApplication<ApplicationMain>().getInstance().movieDao()
    var id = MutableLiveData<Int>()

    init {

        (application as ApplicationMain).restComponent?.inject(this)
        repository = MovieRepository(apiService)

        //    apiResponseObservable = repository.getRelatedMovies(id.toString())

        apiResponseObservable = Transformations.switchMap(id, {
            repository.getRelatedMovies(id.value.toString())
        })
    }

    fun getRelatedMoviesObservable(): LiveData<MovieResponse> = apiResponseObservable

    fun requestRelatedMovies(pageId: Int) {

        id.value = pageId
    }

    fun isMovieFavorite(title: String): Boolean {
        return CheckFavMovieTask().execute(title).get()
    }

    fun addToFavourites(movie: Movie) {
        InsertFavMovie().execute(movie)
    }


    fun deleteItem(movie: Movie) {
        DeleteFavMovie().execute(movie.title)
    }

    inner class CheckFavMovieTask : AsyncTask<String, String, Boolean>() {

        override fun doInBackground(vararg params: String): Boolean {

            val movies = movieDao.getMovieByTitle(params[0])

            if (movies != null) {
                return true
            }
            return false
        }
    }

    inner class InsertFavMovie : AsyncTask<Movie, String, String>() {

        override fun doInBackground(vararg params: Movie): String? {

            movieDao.insertMovie(params[0])
            return null
        }
    }

    inner class DeleteFavMovie : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg params: String): String? {

            movieDao.deleteMovieByTitle(params[0])
            return null
        }
    }

    fun setIsMovieFavIcon(title: String, floatingButton: FloatingActionButton) {

        if (isMovieFavorite(title)) {

            floatingButton.setImageResource(R.drawable.ic_favorite_selected)
        } else {
            floatingButton.setImageResource(R.drawable.ic_favorite_unselected)
        }
    }
}