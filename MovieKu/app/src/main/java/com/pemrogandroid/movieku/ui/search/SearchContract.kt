package com.pemrogandroid.movieku.ui.search

import com.pemrogandroid.movieku.model.Movie
import com.pemrogandroid.movieku.model.TmdbResponse

class SearchContract {

    interface PresenterInterface{
        fun getSearchResult(query: String)
        fun onStop()

    }

    interface ViewInterface{
        fun displayError(message:String)

        fun displayMessage(message:String)

        fun displayResult(tmdbResponse: TmdbResponse)

    }
}