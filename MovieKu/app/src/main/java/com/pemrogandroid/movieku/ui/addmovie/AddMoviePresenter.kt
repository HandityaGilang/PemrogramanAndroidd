package com.pemrogandroid.movieku.ui.addMovie

import com.pemrogandroid.movieku.model.Movie
import com.pemrogandroid.movieku.repository.LocalDataSource

class AddMoviePresenter(
    private var viewInterface: AddMovieContract.ViewInterface,
    private var dataSource: LocalDataSource

) : AddMovieContract.PresenterInterface{
    override fun addMovie(id: Int, title: String, releaseDate: String, posterPath: String) {
        if (title.isEmpty()) {
            viewInterface.displayError("movie belum dipilih")
        } else {
            val movie = Movie(id,title, releaseDate, posterPath)
            dataSource.insert(movie)

            viewInterface.returnToMain()
        }
    }


}