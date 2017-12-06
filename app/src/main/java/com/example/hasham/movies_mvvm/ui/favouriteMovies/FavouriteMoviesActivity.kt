package com.example.hasham.movies_mvvm.ui.favouriteMovies

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.example.hasham.movies_mvvm.BR
import com.example.hasham.movies_mvvm.R
import com.example.hasham.movies_mvvm.ViewModelProviderFactory
import com.example.hasham.movies_mvvm.data.models.Movie
import com.example.hasham.movies_mvvm.databinding.ActivityFavouriteMoviesBinding
import com.example.hasham.movies_mvvm.ui.ActivityBindingProvider
import com.example.hasham.movies_mvvm.ui.RecyclerBindingAdapter
import java.util.AbstractList

class FavouriteMoviesActivity : AppCompatActivity(), FavouriteMoviesNavigator {

    private lateinit var viewModel: FavouriteMoviesViewModel
    private val binding: ActivityFavouriteMoviesBinding by ActivityBindingProvider(R.layout.activity_favourite_movies)
    private val mAdapter: RecyclerBindingAdapter<Movie> = RecyclerBindingAdapter(R.layout.activity_favourite_movies, BR.favourite)
    private lateinit var liveData: LiveData<List<Movie>>


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

        liveData.observe(this, object : Observer<List<Movie>> {
            override fun onChanged(t: List<Movie>?) {
                if (t != null)
                    mAdapter!!.addItems(t as AbstractList<Movie>)
            }

        })

    }
}