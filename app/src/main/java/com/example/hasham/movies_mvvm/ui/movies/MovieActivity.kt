package com.example.hasham.movies_mvvm.ui.movies

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.example.hasham.movies_mvvm.R
import com.example.hasham.movies_mvvm.ViewModelProviderFactory
import com.example.hasham.movies_mvvm.data.models.ApiResponse
import com.example.hasham.movies_mvvm.data.remote.API
import com.example.hasham.movies_mvvm.data.repository.MovieRepository
import com.example.hasham.movies_mvvm.databinding.ActivityMovieBinding
import com.example.hasham.movies_mvvm.util.RecyclerCustomAdapter
import javax.inject.Inject

class MovieActivity : AppCompatActivity(), MovieNavigator {

    private lateinit var viewModel: MovieViewModel
    private lateinit var binding: ActivityMovieBinding
    private lateinit var adapter: RecyclerCustomAdapter
    private var currentPage = 1
    private var pastVisibleItems: Int = 0
    private var visibleItemCount:Int = 0
    private var totalItemCount:Int = 0
    private var requestLoading = false
    private val is_last_page = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)
        viewModel = ViewModelProviderFactory(MovieViewModel(application, this)).create(MovieViewModel::class.java)
        adapter = RecyclerCustomAdapter()

        val layoutManager = GridLayoutManager(this, resources.getInteger(R.integer.columns))

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

        viewModel.getMoviesObservable().observe(this, Observer<ApiResponse> { resp ->

            Log.e("movies", resp?.results?.size.toString() + "   .")

            binding.recyclerViewMain.layoutManager = layoutManager
            binding.recyclerViewMain.setHasFixedSize(true)
            binding.recyclerViewMain.setItemViewCacheSize(20)
            binding.recyclerViewMain.isDrawingCacheEnabled = true
            binding.recyclerViewMain.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
            binding.recyclerViewMain.adapter = adapter
            adapter.setProjectList(resp!!.results)

            Log.e("response", resp.toString())

         //   viewModel.requestNextPage(currentPage++)

            binding.recyclerViewMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    if (dy > 0)
                    //check for scroll down
                    {
                        visibleItemCount = layoutManager.childCount
                        totalItemCount = layoutManager.itemCount
                        pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                        if (!requestLoading && !is_last_page) {
                            if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                                requestLoading = true

                                currentPage++
                                viewModel.requestNextPage(currentPage)
                                Log.v("scrolling", "scroll down" + currentPage)


                            }
                        }
                    }
                }
            })
        })

        viewModel.getMoviesObservable().observe(this, Observer<ApiResponse> {

        })

        viewModel.requestNextPage(currentPage)
    }
}
