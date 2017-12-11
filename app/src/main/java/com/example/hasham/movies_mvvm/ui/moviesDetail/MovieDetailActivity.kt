@file:Suppress("SENSELESS_COMPARISON")

package com.example.hasham.movies_mvvm.ui.moviesDetail

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.example.hasham.movies_mvvm.BR
import com.example.hasham.movies_mvvm.R
import com.example.hasham.movies_mvvm.ViewModelProviderFactory
import com.example.hasham.movies_mvvm.data.models.Movie
import com.example.hasham.movies_mvvm.data.models.MovieResponse
import com.example.hasham.movies_mvvm.databinding.ActivityMovieDetailBinding
import com.example.hasham.movies_mvvm.ui.ActivityBindingProvider
import com.example.hasham.movies_mvvm.ui.RecyclerBindingAdapter

class MovieDetailActivity : AppCompatActivity(), MovieDetailNavigator, RecyclerBindingAdapter.OnItemClickListener<Movie> {

    private lateinit var viewModel: MovieDetailViewModel
    private val binding: ActivityMovieDetailBinding by ActivityBindingProvider(R.layout.activity_movie_detail)
    private val mAdapter: RecyclerBindingAdapter<Movie> = RecyclerBindingAdapter(R.layout.list_item_movie_horizontal, BR.movie)
    private lateinit var movieListObserver: Observer<MovieResponse>
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

        val bundle = intent.extras

        if (bundle != null) {

            movie = bundle.getParcelable("MovieObject")
            binding.setVariable(BR.detail, movie)
            title = movie.originalTitle
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

        movieListObserver = Observer { resp ->

            mAdapter.addItems(resp?.results as ArrayList<Movie>)
        }


        binding.floatingButton.setOnClickListener {

            if (movie != null) {
                viewModel.addToFavourites(movie)
            }
        }

        mAdapter.setOnItemClickListener(this)
    }

    override fun onItemClick(position: Int, item: Movie) {

        startActivity(Intent(this, MovieDetailActivity::class.java).putExtra("MovieObject", item))
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onStart() {
        super.onStart()

        viewModel.getRelatedMoviesObservable().observe(this, movieListObserver)
        viewModel.requestRelatedMovies(movie.id as Int)
    }

    override fun onDestroy() {

        viewModel.getRelatedMoviesObservable().removeObserver(movieListObserver)
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
    }
}
