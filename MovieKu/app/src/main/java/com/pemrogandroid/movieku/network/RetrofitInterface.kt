package com.pemrogandroid.movieku.network

import com.pemrogandroid.movieku.model.MovieDetailsResponse
import com.pemrogandroid.movieku.model.TmdbResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("search/movie")
    fun searchMovie(@Query("api_key") api_key: String, @Query("query") query: String)
            : Observable<TmdbResponse>
    //"https://api.themoviedb.org/3/movie/ID_MOVIE?api_key=f219b8f04b716dab27f0a1c400ff9dab"
    @GET("movie/{query}")
    fun searchMovie2(@Path("query") query: Int, @Query("api_key") api_key: String)
            : Observable<MovieDetailsResponse>

}