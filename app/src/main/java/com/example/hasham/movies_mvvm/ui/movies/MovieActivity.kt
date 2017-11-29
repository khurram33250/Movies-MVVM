package com.example.hasham.movies_mvvm.ui.movies

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import com.example.hasham.movies_mvvm.ApplicationMain
import com.example.hasham.movies_mvvm.R
import com.example.hasham.movies_mvvm.ViewModelProviderFactory
import com.example.hasham.movies_mvvm.data.models.ApiResponse
import com.example.hasham.movies_mvvm.data.models.Movie
import com.example.hasham.movies_mvvm.databinding.ActivityMovieBinding
import retrofit2.Retrofit
import javax.inject.Inject

class MovieActivity : AppCompatActivity(), MovieNavigator {

    private lateinit var viewModel: MovieViewModel
    private lateinit var binding: ActivityMovieBinding
    private lateinit var movieData: LiveData<List<Movie>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)

        val layoutManager = GridLayoutManager(this, resources.getInteger(R.integer.columns))

        binding.recyclerViewMain.layoutManager = layoutManager
        binding.recyclerViewMain.setHasFixedSize(true)
        binding.recyclerViewMain.setItemViewCacheSize(20)
        binding.recyclerViewMain.isDrawingCacheEnabled = true
        binding.recyclerViewMain.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH

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

        var currentPage = 1

        viewModel = ViewModelProviderFactory(MovieViewModel(application, this)).create(MovieViewModel::class.java)

        viewModel.getMoviesObservable().observe(this, Observer<ApiResponse> { resp ->

            Log.e("movies", resp?.results?.size.toString() + "   .")

//            viewModel.requestNextPage(currentPage++)

        })

        viewModel.requestNextPage(currentPage)


    }
}
