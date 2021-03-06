package com.example.hasham.movies_mvvm.di.components

import com.example.hasham.movies_mvvm.di.modules.AppModule
import com.example.hasham.movies_mvvm.di.modules.NetModule
import com.example.hasham.movies_mvvm.ui.BaseActivity
import com.example.hasham.movies_mvvm.ui.movies.MovieActivity
import com.example.hasham.movies_mvvm.ui.movies.MovieViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Developed by Hasham.Tahir on 1/5/2017.
 */

@Singleton
@Component(modules = arrayOf(AppModule::class, NetModule::class))
interface RestComponent {
    fun inject(activity: BaseActivity)
    fun inject(activity: MovieActivity)
    fun inject(viewModel: MovieViewModel)
}