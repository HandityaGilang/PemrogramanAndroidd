package com.pemrogandroid.movieku.model

import androidx.room.Entity
import androidx.room.PrimaryKey

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_table")
open class Movie {
    @SerializedName("vote_count")
    @Expose
    var voteCount: Int? = null

    @PrimaryKey
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("video")
    @Expose
    var video: Boolean? = null

    @SerializedName("vote_average")
    @Expose
    var voteAverage: Float? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("popularity")
    @Expose
    var popularity: Float? = null

    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null

    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = null

    @SerializedName("original_title")
    @Expose
    var originalTitle: String? = null

    @SerializedName("genre_ids")
    @Expose
    var genreIds: List<Int>? = null

    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null

    @SerializedName("adult")
    @Expose
    var adult: Boolean? = null

    @SerializedName("overview")
    @Expose
    var overview: String? = null

    @SerializedName("release_date")
    @Expose
    var releaseDate: String? = null
    var watched: Boolean = false

    /*
    no argument
     */
    constructor(){

    }

    constructor(id: Int, title: String, releaseDate: String, posterPath: String){
        this.id = id
        this.title=title
        this.releaseDate = releaseDate
        this.posterPath = posterPath

    }
    constructor(
        voteCount: Int?,
        id: Int?,
        video: Boolean?,
        voteAverage: Float?,
        title: String?,
        popularity: Float?,
        posterPath: String?,
        originalLanguage: String?,
        originalTitle: String?,
        genreIds: List<Int>?,
        backdropPath: String?,
        adult: Boolean?,
        overview: String?,
        releaseDate: String?,
        watched: Boolean
    ) {
        this.voteCount = voteCount
        this.id = id
        this.video = video
        this.voteAverage = voteAverage
        this.title = title
        this.popularity = popularity
        this.posterPath = posterPath
        this.originalLanguage = originalLanguage
        this.originalTitle = originalTitle
        this.genreIds = genreIds
        this.backdropPath = backdropPath
        this.adult = adult
        this.overview = overview
        this.releaseDate = releaseDate
        this.watched = watched
    }

    fun getReleaseYearFromDate(): String? {
        return releaseDate?.split("-")?.get(0)
    }
}
