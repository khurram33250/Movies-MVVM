package com.example.hasham.movies_mvvm.ui.moviesDetail

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.hasham.movies_mvvm.BR
import com.example.hasham.movies_mvvm.R
import com.example.hasham.movies_mvvm.ViewModelProviderFactory
import com.example.hasham.movies_mvvm.data.models.ApiResponse
import com.example.hasham.movies_mvvm.data.models.Movie
import com.example.hasham.movies_mvvm.databinding.ActivityMovieDetailBinding
import com.example.hasham.movies_mvvm.ui.ActivityBindingProvider
import com.example.hasham.movies_mvvm.ui.RecyclerBindingAdapter

class MovieDetailActivity : AppCompatActivity(), MovieDetailNavigator {

    private lateinit var viewModel: MovieDetailViewModel
    private val binding: ActivityMovieDetailBinding by ActivityBindingProvider(R.layout.activity_movie_detail)
    private val mAdapter: RecyclerBindingAdapter<Movie> = RecyclerBindingAdapter(R.layout.list_item_movie, BR.movie)
    private lateinit var movieListObserver: Observer<ApiResponse>
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviderFactory(MovieDetailViewModel(application, this)).create(MovieDetailViewModel::class.java)

        val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        setSupportActionBar(binding.toolbar)

        try {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

        binding.recyclerViewDetail.apply {

            layoutManager = mLayoutManager
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            binding.recyclerViewDetail.isNestedScrollingEnabled = false
            isDrawingCacheEnabled = true
            drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
            adapter = mAdapter
        }

        val extras = intent.extras
        movie = extras.getParcelable<Movie>("MovieObject")

        if (true) {
            binding.setVariable(BR.detail, movie)
            title = movie.originalTitle
            //    getRelatedMovies(movie.getId())

/*            viewModel.getRelatedMoviesObservable().observe(this, movieListObserver)
            movie.id?.let { viewModel.requestRelatedMovies(it) }*/
        }

/*        movieListObserver = Observer { resp ->

            mAdapter.addItems(resp?.results as ArrayList<Movie>)
            Log.v("moviesRow", resp.results.toString())
        }*/

        binding.floatingButton.setOnClickListener {
            Log.v("clicked", "floating button")
            var favmovie= Movie(1, true, "", 1, "" , 1, "", "", "", "", 0.0, "", "", 1, 1, "", "", "", true, 1.0, 1, "")
            viewModel.addToFavourites(favmovie)
        }
    }

    override fun onStart() {
        super.onStart()

//        viewModel.getRelatedMoviesObservable().observe(this, movieListObserver)
//        movie.id?.let { viewModel.requestRelatedMovies(it) }
    }

    override fun onDestroy() {

        viewModel.getRelatedMoviesObservable().removeObserver(movieListObserver)
        super.onDestroy()
    }
}
