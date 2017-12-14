package com.example.hasham.movies_mvvm.ui.movies

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.hasham.movies_mvvm.BR
import com.example.hasham.movies_mvvm.R
import com.example.hasham.movies_mvvm.ViewModelProviderFactory
import com.example.hasham.movies_mvvm.data.models.Movie
import com.example.hasham.movies_mvvm.data.models.MovieResponse
import com.example.hasham.movies_mvvm.databinding.ActivityMovieBinding
import com.example.hasham.movies_mvvm.ui.ActivityBindingProvider
import com.example.hasham.movies_mvvm.ui.RecyclerBindingAdapter
import com.example.hasham.movies_mvvm.ui.favouriteMovies.FavouriteMoviesActivity
import com.example.hasham.movies_mvvm.ui.moviesDetail.MovieDetailActivity
import com.example.hasham.movies_mvvm.util.HAlert

class MovieActivity : AppCompatActivity(), MovieNavigator {

    private lateinit var viewModel: MovieViewModel
    private val binding: ActivityMovieBinding by ActivityBindingProvider(R.layout.activity_movie)
    private val movieAdapter: RecyclerBindingAdapter<Movie> = RecyclerBindingAdapter(R.layout.list_item_movie, BR.movie)
    private val dramaAdapter: RecyclerBindingAdapter<Movie> = RecyclerBindingAdapter(R.layout.list_item_movie_horizontal, BR.movie)
    private lateinit var movieListObserver: Observer<MovieResponse>
    private lateinit var dramaListObserver: Observer<MovieResponse>
    private var currentMoviePage = 1
    private var currentDramaPage = 1
    private var pastVisibleItems: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var requestLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviderFactory(MovieViewModel(application, this)).create(MovieViewModel::class.java)

        val gridLayoutManager = GridLayoutManager(this, resources.getInteger(R.integer.columns))
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.recyclerViewMain.apply {

            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            isDrawingCacheEnabled = true
            drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
            adapter = movieAdapter
            isNestedScrollingEnabled = false
        }

        binding.recyclerViewDrama.apply {

            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            isDrawingCacheEnabled = true
            drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
            adapter = dramaAdapter
        }

//        binding.recyclerViewMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
//                if (dy > 0)
//                {
//                    visibleItemCount = gridLayoutManager.childCount
//                    totalItemCount = gridLayoutManager.itemCount
//                    pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition()
//
//                    if (!requestLoading && !viewModel.isLastMoviePage(currentMoviePage)) {
//                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
//                            requestLoading = true
//                            val requestPage = currentMoviePage + 1
//
//                            viewModel.requestMovies(requestPage)
//
//                        }
//                    }
//                }
//            }
//        })

        binding.nestedScroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->

            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {

                currentMoviePage++
                viewModel.requestMovies(currentMoviePage)
                HAlert.showToast(this, currentMoviePage.toString())

            }
        })

        dramaListObserver = Observer { resp ->

            dramaAdapter.addItems(resp?.results as ArrayList<Movie>)
        }

        movieListObserver = Observer { resp ->

            requestLoading = false
            movieAdapter.addItems(resp?.results as ArrayList<Movie>)
        }

        viewModel.getMoviesObservable().observe(this, movieListObserver)
        viewModel.requestMovies(currentMoviePage)

        viewModel.getDramasObservable().observe(this, dramaListObserver)
        viewModel.requestDramas(currentDramaPage)

        movieAdapter.setOnItemClickListener(object : RecyclerBindingAdapter.OnItemClickListener<Movie> {
            override fun onItemClick(position: Int, item: Movie) {

                startActivity(Intent(this@MovieActivity, MovieDetailActivity::class.java).putExtra("MovieObject", item))
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
            }
        })

        dramaAdapter.setOnItemClickListener(object : RecyclerBindingAdapter.OnItemClickListener<Movie> {

            override fun onItemClick(position: Int, item: Movie) {

                startActivity(Intent(this@MovieActivity, MovieDetailActivity::class.java).putExtra("MovieObject", item))
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {

                val intent = Intent(this, FavouriteMoviesActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)

        }
    }

    override fun onDestroy() {

        viewModel.getMoviesObservable().removeObserver(movieListObserver)
        viewModel.getDramasObservable().removeObserver(dramaListObserver)
        super.onDestroy()
    }
}
