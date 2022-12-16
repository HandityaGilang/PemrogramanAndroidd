package com.pemrogandroid.movieku.ui.DetailMovie

import android.util.Log
import com.pemrogandroid.movieku.model.MovieDetailsResponse
import com.pemrogandroid.movieku.repository.RemoteDataSource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class DetailMoviePresenter(
    private var viewInterface: DetailMovieContract.ViewInterface,
    private var remoteDataSource: RemoteDataSource = RemoteDataSource()



) : DetailMovieContract.PresenterInterface {
    private val TAG = "DetailPresenter"
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val searchResultsObservable: (String) -> Observable<MovieDetailsResponse>? =
        { query ->
            remoteDataSource.searchResultObserveableID(query.toInt())
        }



    val resultObserver: DisposableObserver<MovieDetailsResponse>?
        get() = object : DisposableObserver<MovieDetailsResponse>() {
            override fun onNext(response: MovieDetailsResponse) {
                Log.i(TAG, "resultObserver: " + response)
                viewInterface.displayResult(response)
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError: " + e.message)
                viewInterface.displayError("data tidak ditemukan")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
            }
        }

    override fun getSearchResult(query: String) {
        val searchResultDisposable = searchResultsObservable(query)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeWith(resultObserver)

        compositeDisposable.add(searchResultDisposable!!)


    }

    override fun onStop() {
        compositeDisposable.clear()
    }
}