package com.pemrogandroid.movieku.repository

import android.app.Application
import com.pemrogandroid.movieku.db.LocalDatabase
import com.pemrogandroid.movieku.db.MovieDao
import com.pemrogandroid.movieku.model.Movie
import io.reactivex.Observable
import kotlin.concurrent.thread

open class LocalDataSource(application: Application) {

    private val movieDao: MovieDao
    open val allMovies: Observable<List<Movie>>

    init {
        val db = LocalDatabase.getInstance(application)
        movieDao = db.movieDao()
        allMovies = movieDao.all
    }

    fun insert(movie: Movie) {
        thread {
            movieDao.insert(movie)
        }
    }

    fun delete(movie: Movie) {
        thread {
            movieDao.delete(movie.id)
        }
    }

    fun update(movie: Movie) {
        thread {
            movieDao.update(movie)
        }
    }
}