package com.example.hasham.movies_mvvm.di.modules

import android.app.Application

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

/**
 * Developed by Hasham.Tahir on 1/5/2017.
 */

@Module
class AppModule(private var mApplication: Application) {

    @Provides
    @Singleton
    internal fun providesApplication(): Application = mApplication

}
