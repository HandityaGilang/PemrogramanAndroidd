package com.pemrogandroid.movieku.ui.search

import android.util.Log
import android.widget.Toast
import com.pemrogandroid.movieku.model.TmdbResponse
import com.pemrogandroid.movieku.repository.RemoteDataSource
import com.pemrogandroid.movieku.ui.mainmenu.MainContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class SearchPresenter (
    private var viewInterface: SearchContract.ViewInterface,
    private var remoteDataSource: RemoteDataSource = RemoteDataSource()
) : SearchContract.PresenterInterface{
    private val TAG = "SearchPresenter"
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val searchResultsObservable: (String) -> Observable<TmdbResponse> =
        { query ->
            remoteDataSource.searchResultObserveable(query)
        }

    val resultObserver: DisposableObserver<TmdbResponse>
        get() = object : DisposableObserver<TmdbResponse>()  {
            override fun onNext(response: TmdbResponse) {
                Log.i(TAG, "resultObserver: "+response.totalResults)
                viewInterface.displayResult(response)
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError: "+e.message)
                viewInterface.displayError("Error fetching Movie Data")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
            }
        }

    override fun getSearchResult(query: String) {
        val searchResultDisposable = searchResultsObservable(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(resultObserver)

        compositeDisposable.add(searchResultDisposable)
    }

    override fun onStop() {
        compositeDisposable.clear()
    }


}