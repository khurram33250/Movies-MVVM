package com.example.hasham.movies_mvvm.ui.movies

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.hasham.movies_mvvm.BR
import com.example.hasham.movies_mvvm.R
import com.example.hasham.movies_mvvm.ViewModelProviderFactory
import com.example.hasham.movies_mvvm.data.models.ApiResponse
import com.example.hasham.movies_mvvm.data.models.Movie
import com.example.hasham.movies_mvvm.databinding.ActivityMovieBinding
import com.example.hasham.movies_mvvm.ui.ActivityBindingProvider
import com.example.hasham.movies_mvvm.ui.RecyclerBindingAdapter

class MovieActivity : AppCompatActivity(), MovieNavigator, RecyclerBindingAdapter.OnItemClickListener<Movie> {

    private lateinit var viewModel: MovieViewModel
    private val binding: ActivityMovieBinding by ActivityBindingProvider(R.layout.activity_movie)
    private val mAdapter: RecyclerBindingAdapter<Movie> = RecyclerBindingAdapter(R.layout.list_item_movie, BR.movie)
    private lateinit var movieListObserver: Observer<ApiResponse>
    private var currentPage = 1
    private var pastVisibleItems: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var requestLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviderFactory(MovieViewModel(application, this)).create(MovieViewModel::class.java)

        val mLayoutManager = GridLayoutManager(this, resources.getInteger(R.integer.columns))

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

        binding.recyclerViewMain.apply {

            layoutManager = mLayoutManager
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            isDrawingCacheEnabled = true
            drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
            adapter = mAdapter
        }

        binding.recyclerViewMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0)
                //check for scroll down
                {
                    visibleItemCount = mLayoutManager.childCount
                    totalItemCount = mLayoutManager.itemCount
                    pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition()

                    if (!requestLoading && !viewModel.isLastPage(currentPage)) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            requestLoading = true

                            val requestPage = currentPage + 1
                            viewModel.requestMovies(requestPage)

                        }
                    }
                }
            }
        })

        movieListObserver = Observer { resp ->

            requestLoading = false
            mAdapter.addItems(resp?.results as ArrayList<Movie>)

        }

        mAdapter.setOnItemClickListener(this)

    }

    override fun onItemClick(position: Int, item: Movie) {

    }

    override fun onStart() {
        super.onStart()

        viewModel.getMoviesObservable().observe(this, movieListObserver)
        viewModel.requestMovies(currentPage)
    }

    override fun onDestroy() {

        viewModel.getMoviesObservable().removeObserver(movieListObserver)
        super.onDestroy()
    }
}
