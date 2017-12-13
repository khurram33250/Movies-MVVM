package com.example.hasham.movies_mvvm.ui.movies

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.hasham.movies_mvvm.BR
import com.example.hasham.movies_mvvm.R
import com.example.hasham.movies_mvvm.ViewModelProviderFactory
import com.example.hasham.movies_mvvm.data.models.Drama
import com.example.hasham.movies_mvvm.data.models.DramaResponse
import com.example.hasham.movies_mvvm.data.models.Movie
import com.example.hasham.movies_mvvm.data.models.MovieResponse
import com.example.hasham.movies_mvvm.databinding.ActivityMovieBinding
import com.example.hasham.movies_mvvm.ui.ActivityBindingProvider
import com.example.hasham.movies_mvvm.ui.RecyclerBindingAdapter
import com.example.hasham.movies_mvvm.ui.moviesDetail.MovieDetailActivity
import com.example.hasham.movies_mvvm.util.HAlert

class MovieActivity : AppCompatActivity(), MovieNavigator, RecyclerBindingAdapter.OnMovieItemClickListener<Movie>,
        RecyclerBindingAdapter.OnDramaItemClickListener<Drama> {

    private lateinit var viewModel: MovieViewModel
    private val binding: ActivityMovieBinding by ActivityBindingProvider(R.layout.activity_movie)
    private val movieAdapter: RecyclerBindingAdapter<Movie> = RecyclerBindingAdapter(R.layout.list_item_movie, BR.movie)
    private val dramaAdapter: RecyclerBindingAdapter<Drama> = RecyclerBindingAdapter(R.layout.list_item_drama_horizontal, BR.drama)
    private lateinit var movieListObserver: Observer<MovieResponse>
    private lateinit var dramaListObserver: Observer<DramaResponse>
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

        val filterSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)

        filterSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    filterSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
        filterSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        binding.recyclerViewMain.isNestedScrollingEnabled = false
        binding.recyclerViewMain.apply {

            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            isDrawingCacheEnabled = true
            drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
            adapter = movieAdapter
        }

        binding.recyclerViewDrama.apply {

            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            isDrawingCacheEnabled = true
            drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
            adapter = dramaAdapter
        }

//        binding.recyclerViewDrama.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
//                if (dy > 0)
//                //check for scroll down
//                {
//                    visibleItemCount = gridLayoutManager.childCount
//                    totalItemCount = gridLayoutManager.itemCount
//                    pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition()
//
//                    if (!requestLoading && !viewModel.isLastMoviePage(currentDramaPage)) {
//                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
//                            requestLoading = true
//                            val requestPage = currentDramaPage + 1
//
//                            viewModel.requestDramas(requestPage)
//
//                        }
//                    }
//                }
//            }
//        })

        binding.recyclerViewMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0)
                //check for scroll down
                {
                    visibleItemCount = gridLayoutManager.childCount
                    totalItemCount = gridLayoutManager.itemCount
                    pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition()

                    if (!requestLoading && !viewModel.isLastMoviePage(currentMoviePage)) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            requestLoading = true
                            val requestPage = currentMoviePage + 1

                            viewModel.requestMovies(requestPage)

                        }
                    }
                }
            }
        })

        dramaListObserver = Observer { resp ->

            //    requestLoading = false
            dramaAdapter.addDramaItems(resp?.results as ArrayList<Drama>)
        }

        movieListObserver = Observer { resp ->

            requestLoading = false
            movieAdapter.addMovieItems(resp?.results as ArrayList<Movie>)
        }

        //    movieAdapter.setOnItemClickListener(this)
        movieAdapter.setOnMovieItemClickListener(this)
        dramaAdapter.setOnDramaItemClickListener(this)

    }

//    override fun onItemClick(position: Int, item: Movie) {
//
//        Log.v("SingleMovie", item.toString())
//
//        startActivity(Intent(this, MovieDetailActivity::class.java).putExtra("MovieObject", item))
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
//
//    }

    override fun onMovieItemClick(position: Int, item: Movie) {

        Log.v("SingleMovie", item.toString())

        startActivity(Intent(this, MovieDetailActivity::class.java).putExtra("MovieObject", item))
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
    }

    override fun onDramaItemClick(position: Int, item: Drama) {

        HAlert.showToast(this, item.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {

                return true
            }
            else -> return super.onOptionsItemSelected(item)

        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.getMoviesObservable().observe(this, movieListObserver)
        viewModel.requestMovies(currentMoviePage)

        viewModel.getDramasObservable().observe(this, dramaListObserver)
        viewModel.requestDramas(currentDramaPage)

    }

    override fun onDestroy() {

        viewModel.getMoviesObservable().removeObserver(movieListObserver)
        viewModel.getDramasObservable().removeObserver(dramaListObserver)
        super.onDestroy()
    }
}
