package com.pemrogandroid.catatantempat.ui

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.pemrogandroid.catatantempat.databinding.ActivityBookmarkDetailsBinding
import com.pemrogandroid.catatantempat.viewmodel.BookmarkDetailsViewModel

class BookmarkDetailsActivity : AppCompatActivity {
    private lateinit var binding:ActivityBookmarkDetailsBinding
    private val bookmarkDetailsViewModel by viewModels<BookmarkDetailsViewModel> {  }

    private var bookmarkDetailsView: BookmarkDetailsViewModel.BookmarksDetailsView = null

}