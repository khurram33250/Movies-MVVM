package com.example.hasham.projectk.data.local.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.hasham.movies_mvvm.data.models.Movie


/**
 * Developed by hasham on 11/20/17.
 */

@Database(entities = arrayOf(Movie::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}