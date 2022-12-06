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
import com.pemrogandroid.movieku.ui.addmovie.AddMovieActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    lateinit var binding: ActivityMainBinding
    private var adapter: MainAdapter? = null
    private lateinit var dataSource: LocalDataSource

    private lateinit var activityAddMovieResultLaucher: ActivityResultLauncher<Intent>

    private val compositeDisposable = CompositeDisposable()

    companion object {
        val EXTRA_ID = "DetailsActivity.MOVIE_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.moviesRecyclerview.layoutManager = LinearLayoutManager(this)
        supportActionBar?.title = "Movies Tontonanku"

        activityAddMovieResultLaucher = registerForActivityResult(
            ActivityResultContracts
                .StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                Toast.makeText(this@MainActivity,"Movie berhasil ditambahkan.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@MainActivity,"Movie gagal ditambahkan.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dataSource = LocalDataSource(application)
        getMyMoviesList()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    private val myMoviesObservable: Observable<List<Movie>>
        get() = dataSource.allMovies


    private val observer: DisposableObserver<List<Movie>>
        get() = object : DisposableObserver<List<Movie>>() {

            override fun onNext(movieList: List<Movie>) {
                displayMovies(movieList)
            }

            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
                Toast.makeText(this@MainActivity,"Error fetching movie list", Toast.LENGTH_LONG).show()
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
            }
        }

    fun displayMovies(movieList: List<Movie>?) {
        if (movieList == null || movieList.size == 0) {
            Log.d(TAG, "No movies to display")
            binding.moviesRecyclerview.visibility = INVISIBLE
            binding.noMoviesLayout.visibility = VISIBLE
        } else {
            adapter = MainAdapter(movieList, this@MainActivity)
            binding.moviesRecyclerview.adapter = adapter

            binding.moviesRecyclerview.visibility = VISIBLE
            binding.noMoviesLayout.visibility = INVISIBLE
        }
    }

    private fun getMyMoviesList() {
        val myMoviesDisposable = myMoviesObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)

        compositeDisposable.add(myMoviesDisposable)
    }

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
                    Toast.makeText(this@MainActivity,"Movie berhasil dihapus", Toast.LENGTH_LONG).show()
                } else if (adapter.selectedMovies.size > 1) {
                    Toast.makeText(this@MainActivity,"Beberapa movie berhasil dihapus", Toast.LENGTH_LONG).show()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}