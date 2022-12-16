package com.pemrogandroid.movieku.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"
    const val TMDB_IMAGEURL = "https://image.tmdb.org/t/p/w500/"
    const val TMDB_ID= "https://api.themoviedb.org/3/movie/ID_MOVIE?api_key=API_KEY"

    val movieApi = Retrofit.Builder()
        .baseUrl(TMDB_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(RetrofitInterface::class.java)

//    val movie=Retrofit.Builder()
//        .baseUrl(TMDB_ID)
//        .addConverterFactory(GsonConverterFactory.create())
//        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//        .build()
//        .create(RetrofitInterface::class.java)
}