package com.pemrogandroid.movieku.ui.addmovie

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.pemrogandroid.movieku.databinding.ActivityAddMovieBinding
import com.pemrogandroid.movieku.model.Movie
import com.pemrogandroid.movieku.network.RetrofitClient.TMDB_IMAGEURL
import com.pemrogandroid.movieku.repository.LocalDataSource
import com.pemrogandroid.movieku.ui.addMovie.AddMovieContract
import com.pemrogandroid.movieku.ui.addMovie.AddMoviePresenter
import com.pemrogandroid.movieku.ui.search.SearchActivity
import com.squareup.picasso.Picasso

class AddMovieActivity : AppCompatActivity(), AddMovieContract.ViewInterface {

    lateinit var binding: ActivityAddMovieBinding

    private lateinit var addMoviePresenter: AddMovieContract.PresenterInterface
    private lateinit var activitySearchLauncher: ActivityResultLauncher<Intent>
    val selectedMovies = Movie()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addMoviePresenter = AddMoviePresenter(this, LocalDataSource(application))

        activitySearchLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                this@AddMovieActivity.runOnUiThread {
                    selectedMovies.id = result.data?.getIntExtra(SearchActivity.EXTRA_ID, -1)
                    selectedMovies.title = result.data?.getStringExtra(SearchActivity.EXTRA_TITLE)
                    selectedMovies.releaseDate = result.data?.getStringExtra(SearchActivity.EXTRA_RELEASE_DATE)
                    selectedMovies.posterPath = result.data?.getStringExtra(SearchActivity.EXTRA_POSTER_PATH)

                    binding.movieTitle.setText(selectedMovies.title)
                    binding.movieReleaseDate.setText(selectedMovies.releaseDate)
                    binding.movieImageview.tag = selectedMovies.posterPath
                    Picasso.get().load(TMDB_IMAGEURL + selectedMovies.posterPath)
                        .into(binding.movieImageview)
                }
            }
        }

        binding.searchButton.setOnClickListener({
            goToSearchMovieActivity()
        })

        binding.btnAddMovie.setOnClickListener({
            addMoviePresenter.addMovie(
                selectedMovies.id!!,
                selectedMovies.title!!,
                selectedMovies.releaseDate!!,
                selectedMovies.posterPath!!
            )
        })
    }

    //search onClick
    fun goToSearchMovieActivity() {
        val title = binding.movieTitle.text.toString()
        val intent = Intent(this@AddMovieActivity, SearchActivity::class.java)
        intent.putExtra(SearchActivity.SEARCH_QUERY, title)
        activitySearchLauncher.launch(intent)
    }


    override fun displayMessage(message: String) {
        Toast.makeText(this@AddMovieActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun displayError(message: String) {
        displayMessage(message)
    }

    override fun returnToMain() {
        setResult(RESULT_OK)
        finish()
    }
}