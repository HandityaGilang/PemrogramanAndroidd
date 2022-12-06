package com.pemrogandroid.movieku

import android.app.Application

//singleton Application context
class MovieKuApp: Application() {

    companion object{
        lateinit var instance:MovieKuApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}