package com.pemrogandroid.movieku.ui.DetailMovie

import com.pemrogandroid.movieku.model.MovieDetailsResponse

class DetailMovieContract {

    interface PresenterInterface{
        fun getSearchResult(query: String)
        fun onStop()
    }

    interface ViewInterface{
        fun displayError(message:String)

        fun displayMessage(message:String)

        fun displayResult(detailMovieResponse: MovieDetailsResponse)

    }
}