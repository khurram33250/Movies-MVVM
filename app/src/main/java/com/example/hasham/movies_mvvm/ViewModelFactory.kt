package com.example.hasham.movies_mvvm

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider


/**
 * Developed by hasham on 11/15/17.
 */

class ViewModelProviderFactory<T : Any>(private val viewModel: T) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModel.javaClass)) {
            return viewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}