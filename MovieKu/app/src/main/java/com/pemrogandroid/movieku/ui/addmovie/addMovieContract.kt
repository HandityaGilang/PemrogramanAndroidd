package com.pemrogandroid.movieku.ui.addMovie

class AddMovieContract {

    interface PresenterInterface {
        fun addMovie(id: Int, title:String, releaseDate:String, posterPath:String)

    }

    interface ViewInterface {
        fun displayMessage(message: String)

        fun displayError(message: String)

        fun returnToMain()
    }
}