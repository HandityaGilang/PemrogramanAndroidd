package com.pemrogandroid.movieku.ui.DetailMovie

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pemrogandroid.movieku.databinding.ActivityMovieDetailsBinding
import com.pemrogandroid.movieku.model.MovieDetailsResponse

import com.pemrogandroid.movieku.network.RetrofitClient
import com.pemrogandroid.movieku.repository.RemoteDataSource


import com.squareup.picasso.Picasso


class DetailMovieActivity :AppCompatActivity(), DetailMovieContract.ViewInterface {
    private val TAG = "DetailActivity"
    private lateinit var query: String
    lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var detilPresenter: DetailMovieContract.PresenterInterface
    private var remoteDtSource = RemoteDataSource()
    companion object {
        val ID = "DetailsActivity.MOVIE_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detilPresenter = DetailMoviePresenter(this, remoteDtSource)

        query = intent.getStringExtra(ID)!!
    }

    override fun onStart() {
        super.onStart()
        detilPresenter.getSearchResult(query)
    }

    override fun onStop() {
        super.onStop()
        detilPresenter.onStop()
    }

    override fun displayResult(detailMovieResponse: MovieDetailsResponse) {
            binding.overviewOverview.setText(detailMovieResponse.overview)
            binding.movieImageview.tag = detailMovieResponse.posterPath
            binding.releaseDateTextview.setText(detailMovieResponse.releaseDate)
            binding.titleTextview.setText(detailMovieResponse.title)
            Picasso.get().load(RetrofitClient.TMDB_IMAGEURL + detailMovieResponse.posterPath)
                .into(binding.movieImageview)
    }

    override fun displayError(message: String) {
        displayMessage(message)
    }
    override fun displayMessage(message: String) {
        Toast.makeText(this@DetailMovieActivity, message, Toast.LENGTH_LONG).show()
    }



}