package com.pemrogandroid.movieku.ui.mainmenu

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.pemrogandroid.movieku.R
import com.pemrogandroid.movieku.databinding.ItemMovieMainBinding
import com.pemrogandroid.movieku.model.Movie
import com.pemrogandroid.movieku.network.RetrofitClient
import com.pemrogandroid.movieku.ui.search.SearchActivity

import com.squareup.picasso.Picasso

import java.util.HashSet

class MainAdapter(
    internal var movieList: List<Movie>,
    internal var context: Context,
    var listener: MainActivity.RecyclerItemListener
) : RecyclerView.Adapter<MoviesHolder>() {
    // HashMap to keep track of which items were selected for deletion
    val selectedMovies = HashSet<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val binding = ItemMovieMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = MoviesHolder(binding)
        binding.root.setOnClickListener { v ->
            listener.onItemClick(v, viewHolder.absoluteAdapterPosition)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        holder.binding.titleTextview.text = movieList[position].title
        holder.binding.releaseDateTextview.text = movieList[position].releaseDate
        if (movieList[position].posterPath.equals("")) {
            holder.binding.movieImageview.setImageDrawable(context.getDrawable(R.drawable.ic_local_movies_gray))
        } else {
            Picasso.get().load(RetrofitClient.TMDB_IMAGEURL + movieList[position].posterPath)
                .into(holder.binding.movieImageview)
        }

//        holder.binding.viewer.setOnClickListener {
//
//        }

        holder.binding.checkbox.setOnClickListener {
            if (!selectedMovies.contains(movieList[position])) {
                holder.binding.checkbox.isChecked = true
                selectedMovies.add(movieList[position])
            } else {
                holder.binding.checkbox.isChecked = false
                selectedMovies.add(movieList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun getItemAtPosition(pos: Int): Movie {
        return movieList[pos]
    }
}
