package com.pemrogandroid.movieku.ui.mainmenu

import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.pemrogandroid.movieku.R
import com.pemrogandroid.movieku.model.Movie
import com.pemrogandroid.movieku.repository.LocalDataSource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainPresenter(
    private var viewInterface: MainContract.ViewInterface,
    private var dataSource: LocalDataSource

) : MainContract.PresenterInterface {

//    private var adapter: MainAdapter? = null
    private val TAG = "MainPresenter"
    private val compositeDisposable = CompositeDisposable()

    private val myMoviesObservable: Observable<List<Movie>>
        get() = dataSource.allMovies

    private val observer: DisposableObserver<List<Movie>>
        get() = object : DisposableObserver<List<Movie>>() {

            override fun onNext(movieList: List<Movie>) {
                viewInterface.displayMovies(movieList)
            }

            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
                viewInterface.displayError("no data")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
            }
        }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == R.id.deleteMenuItem) {
//            val adapter = this.adapter
//            if (adapter != null) {
//                for (movie in adapter.selectedMovies) {
//                    dataSource.delete(movie)
//                }
//                if (adapter.selectedMovies.size == 1) {
//                    viewInterface.displayMessage("Movie berhasil dihapus")
//                } else if (adapter.selectedMovies.size > 1) {
//                    viewInterface.displayMessage("Beberapa movie berhasil dihapus")
//                }
//            }
//        }
//        return onOptionsItemSelected(item)
//    }

    override fun getMyMoviesList() {
        val myMoviesDisposable = myMoviesObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)

        compositeDisposable.add(myMoviesDisposable)
    }


    override fun onStop() {
        compositeDisposable.clear()
    }

}