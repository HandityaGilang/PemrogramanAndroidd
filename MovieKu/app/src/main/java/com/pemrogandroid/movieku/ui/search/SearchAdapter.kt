package com.pemrogandroid.movieku.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pemrogandroid.movieku.R
import com.pemrogandroid.movieku.databinding.ItemMovieDetailsBinding
import com.pemrogandroid.movieku.model.Movie
import com.pemrogandroid.movieku.network.RetrofitClient
import com.squareup.picasso.Picasso

class SearchAdapter(
    var movieList: List<Movie>,
    var context: Context,
    var listener: SearchActivity.RecyclerItemListener
) : RecyclerView.Adapter<SearchMoviesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMoviesHolder {
        val binding = ItemMovieDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val viewHolder = SearchMoviesHolder(binding)
        binding.root.setOnClickListener { v ->
            listener.onItemClick(v, viewHolder.absoluteAdapterPosition)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: SearchMoviesHolder, position: Int) {

        holder.binding.titleTextview.text = movieList[position].title
        holder.binding.releaseDateTextview.text = movieList[position].getReleaseYearFromDate()
        holder.binding.overviewOverview.text = movieList[position].overview

        if (movieList[position].posterPath != null) {
            Picasso.get().load(RetrofitClient.TMDB_IMAGEURL + movieList[position].posterPath)
                .into(holder.binding.movieImageview)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun getItemAtPosition(pos: Int): Movie {
        return movieList[pos]
    }
}
