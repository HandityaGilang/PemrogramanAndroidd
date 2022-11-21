package com.pemrogandroid.catatantempat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class BookmarkDetailsViewModel(application: Application):AndroidViewModel(application) {

    data class BookmarksDetailsView(
        var  id:Long? = null,
        var name: String?="",
        var phone : String? ="",

    )
}