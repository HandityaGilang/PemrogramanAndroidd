package com.pemrogandroid.movieku.ui.addmovie

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pemrogandroid.movieku.model.Movie
import com.pemrogandroid.movieku.repository.LocalDataSource

class AddMoviePresenter(
    private  var viewInterface: addMovieContract.ViewInterface,
    private  var dataSource: LocalDataSource
): addMovieContract.PresenterInterfacec {


    override fun addMovie(id: Int, title: String, releaseDate: String, posterPath: String) {
        if (title.isEmpty()) {
            viewInterface.displayError("Movie belum dipilih")
        } else {
            val movie = Movie(id,title,releaseDate,posterPath)
            dataSource.insert(movie)
            viewInterface.returnToMain()
        }
    }

}