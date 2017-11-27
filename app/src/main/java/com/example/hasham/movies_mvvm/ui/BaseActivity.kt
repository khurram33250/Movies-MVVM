package com.example.hasham.movies_mvvm.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import com.example.hasham.movies_mvvm.ApplicationMain
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * Developed by hasham on 11/22/17.
 */
class BaseActivity : AppCompatActivity() {

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as ApplicationMain).restComponent?.inject(this)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}