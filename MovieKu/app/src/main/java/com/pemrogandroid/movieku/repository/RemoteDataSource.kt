package com.pemrogandroid.movieku.repository

import com.pemrogandroid.movieku.MovieKuApp
import com.pemrogandroid.movieku.R
import com.pemrogandroid.movieku.model.TmdbResponse
import com.pemrogandroid.movieku.network.RetrofitClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class RemoteDataSource {

    fun searchResultObserveable(query: String): Observable<TmdbResponse> {
        return RetrofitClient.movieApi
            .searchMovie(MovieKuApp.instance.resources.getString(R.string.TMDB_API_KEY), query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}