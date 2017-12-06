package com.example.hasham.movies_mvvm

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.example.hasham.movies_mvvm.di.components.DaggerRestComponent
import com.example.hasham.movies_mvvm.di.components.RestComponent
import com.example.hasham.movies_mvvm.di.modules.AppModule
import com.example.hasham.movies_mvvm.di.modules.NetModule
import com.example.hasham.projectk.data.local.db.AppDatabase

/**
 * Developed by hasham on 11/15/17.
 */
class ApplicationMain : Application() {

    var restComponent: RestComponent? = null

    override fun onCreate() {
        super.onCreate()

        restComponent = DaggerRestComponent.builder()
                .appModule(AppModule(this))
                .netModule(NetModule(BuildConfig.HOST))
                .build()

    }

    companion object {
        var instance: AppDatabase? = null

    }

    // Get a database instance
    @Synchronized
    fun getInstance(): AppDatabase {
        if (instance == null) {
            instance = create(this@ApplicationMain)
        }
        return instance as AppDatabase
    }

    // Create the database
    fun create(context: Context): AppDatabase {
        val builder = Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "app_db").fallbackToDestructiveMigration()
        return builder.build()
    }

}