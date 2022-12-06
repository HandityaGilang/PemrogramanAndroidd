package com.pemrogandroid.movieku.ui.addmovie

import androidx.appcompat.app.AppCompatActivity

class addMovieContract {

    interface PresenterInterfacec{
        fun addMovie(id : Int, title : String, releaseDate : String, posterPath : String)
    }

    interface ViewInterface{

        fun returnToMain()

        fun displayMessage(message : String)

        fun displayError(message: String)
    }
}