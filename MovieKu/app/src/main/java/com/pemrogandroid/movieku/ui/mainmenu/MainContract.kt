package com.pemrogandroid.movieku.ui.mainmenu

import android.view.MenuItem
import com.pemrogandroid.movieku.model.Movie
import com.pemrogandroid.movieku.model.MovieDetailsResponse

class MainContract {

    interface PresenterInterface{

        fun getMyMoviesList()
        fun onStop()

    }

    interface ViewInterface{
        fun displayError(message:String)

        fun displayMessage(message:String)

        fun displayMovies(movieList: List<Movie>?)

    }
}