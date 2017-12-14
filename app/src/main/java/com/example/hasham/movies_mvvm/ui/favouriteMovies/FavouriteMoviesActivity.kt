package com.example.hasham.movies_mvvm.ui.favouriteMovies

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import com.example.hasham.movies_mvvm.BR
import com.example.hasham.movies_mvvm.R
import com.example.hasham.movies_mvvm.ViewModelProviderFactory
import com.example.hasham.movies_mvvm.data.models.Movie
import com.example.hasham.movies_mvvm.databinding.ActivityFavouriteMoviesBinding
import com.example.hasham.movies_mvvm.ui.ActivityBindingProvider
import com.example.hasham.movies_mvvm.ui.RecyclerBindingAdapter
import com.example.hasham.movies_mvvm.ui.moviesDetail.MovieDetailActivity

class FavouriteMoviesActivity : AppCompatActivity(), FavouriteMoviesNavigator, RecyclerBindingAdapter.OnItemClickListener<Movie> {

    private lateinit var viewModel: FavouriteMoviesViewModel
    private val binding: ActivityFavouriteMoviesBinding by ActivityBindingProvider(R.layout.activity_favourite_movies)
    private val mAdapter: RecyclerBindingAdapter<Movie> = RecyclerBindingAdapter(R.layout.list_item_movie, BR.movie)
    private lateinit var movieListObserver: Observer<List<Movie>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_movies)

        viewModel = ViewModelProviderFactory(FavouriteMoviesViewModel(application, this)).create(FavouriteMoviesViewModel::class.java)

        val mLayoutManager = GridLayoutManager(this, resources.getInteger(R.integer.columns))

        binding.recyclerViewFav.apply {

            layoutManager = mLayoutManager
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            isDrawingCacheEnabled = true
            drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
            adapter = mAdapter
        }

        movieListObserver = Observer { resp ->

            mAdapter.addItems(resp as ArrayList<Movie>)
            Log.e("data", resp.toString())

        }

        mAdapter.setOnItemClickListener(this)
    }

    override fun onItemClick(position: Int, item: Movie) {

        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("MovieObject", item)
        startActivity(intent)
    }

    override fun onStart() {

        super.onStart()
        viewModel.getFavouriteMovies().observe(this, movieListObserver)
    }

    override fun onDestroy() {

        viewModel.getFavouriteMovies().removeObserver(movieListObserver)
        super.onDestroy()
    }
}