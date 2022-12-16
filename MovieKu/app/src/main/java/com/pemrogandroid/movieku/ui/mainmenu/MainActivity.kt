package com.pemrogandroid.movieku.ui.mainmenu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.pemrogandroid.movieku.R
import com.pemrogandroid.movieku.databinding.ActivityMainBinding
import com.pemrogandroid.movieku.model.Movie
import com.pemrogandroid.movieku.repository.LocalDataSource
import com.pemrogandroid.movieku.ui.DetailMovie.DetailMovieActivity
import com.pemrogandroid.movieku.ui.addmovie.AddMovieActivity

class MainActivity : AppCompatActivity(), MainContract.ViewInterface {

    private val TAG = "MainActivity"
    lateinit var binding: ActivityMainBinding
    private var adapter: MainAdapter? = null
    private lateinit var dataSource: LocalDataSource
    private lateinit var mainPresenter: MainContract.PresenterInterface

    private lateinit var activityAddMovieResultLaucher: ActivityResultLauncher<Intent>
    private lateinit var activityDetailActivityResultLaucher: ActivityResultLauncher<Intent>

//    private val compositeDisposable = CompositeDisposable()

    companion object {
        val EXTRA_ID = "DetailsActivity.MOVIE_ID"
    }

    lateinit var idMovie: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.moviesRecyclerview.layoutManager = LinearLayoutManager(this)
        supportActionBar?.title = "Movies Tontonanku"

        mainPresenter = MainPresenter(this, LocalDataSource(application))

        activityAddMovieResultLaucher = registerForActivityResult(
            ActivityResultContracts
                .StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                displayMessage("Movie berhasil ditambahkan.")
//                Toast.makeText(this@MainActivity,"Movie berhasil ditambahkan.", Toast.LENGTH_LONG).show()
            } else {
                displayMessage("Movie gagal ditambahkan.")
//                Toast.makeText(this@MainActivity,"Movie gagal ditambahkan.", Toast.LENGTH_LONG).show()
            }
        }
        activityDetailActivityResultLaucher = registerForActivityResult(
            ActivityResultContracts
                .StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {

            } else {

            }
        }
    }

    internal var itemListener: MainActivity.RecyclerItemListener = object : MainActivity.RecyclerItemListener {
        override fun onItemClick(v: View, position: Int) {
            val movie = adapter?.getItemAtPosition(position)
            idMovie = movie?.id?.toString()!!

            goToDetailActivity()
        }
    }

    private fun goToDetailActivity() {
        val intent = Intent(this@MainActivity, DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.ID, idMovie)
        activityDetailActivityResultLaucher.launch(intent)
    }

    interface RecyclerItemListener {
        fun onItemClick(v: View, position: Int)
    }

    override fun onStart() {
        super.onStart()
        dataSource = LocalDataSource(application)
        mainPresenter.getMyMoviesList()
    }

    override fun onStop() {
        super.onStop()
        mainPresenter.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun displayError(message: String) {
        displayMessage(message)
    }

    override fun displayMessage(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
    }


//    private val myMoviesObservable: Observable<List<Movie>>
//        get() = dataSource.allMovies
//
//
//    private val observer: DisposableObserver<List<Movie>>
//        get() = object : DisposableObserver<List<Movie>>() {
//
//            override fun onNext(movieList: List<Movie>) {
//                displayMovies(movieList)
//            }
//
//            override fun onError(@NonNull e: Throwable) {
//                Log.d(TAG, "Error$e")
//                e.printStackTrace()
//                Toast.makeText(this@MainActivity,"Error fetching movie list", Toast.LENGTH_LONG).show()
//            }
//
//            override fun onComplete() {
//                Log.d(TAG, "Completed")
//            }
//        }

    override fun displayMovies(movieList: List<Movie>?) {
        if (movieList == null || movieList.size == 0) {
            Log.d(TAG, "No movies to display")
            binding.moviesRecyclerview.visibility = INVISIBLE
            binding.noMoviesLayout.visibility = VISIBLE
        } else {
            adapter = MainAdapter(movieList, this@MainActivity,itemListener)
            binding.moviesRecyclerview.adapter = adapter

            binding.moviesRecyclerview.visibility = VISIBLE
            binding.noMoviesLayout.visibility = INVISIBLE
        }
    }

//    private fun getMyMoviesList() {
//        val myMoviesDisposable = myMoviesObservable
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeWith(observer)
//
//        compositeDisposable.add(myMoviesDisposable)
//    }

    fun goToAddMovieActivity(view: View) {
        val intent = Intent(this@MainActivity, AddMovieActivity::class.java)
        activityAddMovieResultLaucher.launch(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteMenuItem) {
            val adapter = this.adapter
            if (adapter != null) {
                for (movie in adapter.selectedMovies) {
                    dataSource.delete(movie)
                }
                if (adapter.selectedMovies.size == 1) {
                    displayMessage("Movie berhasil dihapus")
//                    Toast.makeText(this@MainActivity,"Movie berhasil dihapus", Toast.LENGTH_LONG).show()
                } else if (adapter.selectedMovies.size > 1) {
                    displayMessage("Beberapa movie berhasil dihapus")
//                    Toast.makeText(this@MainActivity,"Beberapa movie berhasil dihapus", Toast.LENGTH_LONG).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}