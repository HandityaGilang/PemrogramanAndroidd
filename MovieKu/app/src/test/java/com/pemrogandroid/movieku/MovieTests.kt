package com.pemrogandroid.movieku
import org.junit.Assert
import org.junit.Test

class MovieTests {

    @Test
    fun testGetReleaseYearFromStringFormattedDate(){
        val movie = Movie(tittle = "Finding Nemo", releaseDate = "2003-05-30")
        Assert.assertEquals("2003",movie.getReleaseYearFromDate()
        )
    }

    fun testGetReleaseYearFromYear(){
        val movie = Movie(tittle = "Finding Nemo", releaseDate = "2003-05-30")
        Assert.assertEquals("2003",movie.getReleaseYearFromDate()
        )
    }

    @Test
    fun testGetReleaseYearCaseEmpty(){
        val movie = Movie(tittle = "Finding Nemo", releaseDate = "")
        Assert.assertEquals("2003",movie.getReleaseYearFromDate()
        )
    }
}