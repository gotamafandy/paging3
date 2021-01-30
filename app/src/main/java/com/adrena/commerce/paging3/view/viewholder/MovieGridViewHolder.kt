package com.adrena.commerce.paging3.view.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.adrena.commerce.paging3.R
import com.adrena.commerce.paging3.data.model.Movies
import com.adrena.commerce.paging3.databinding.MovieGridItemBinding

class MovieGridViewHolder(private val binding: MovieGridItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movies.Movie) {
        with(movie) {
            binding.poster.load(poster?.medium) {
                crossfade(true)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): MovieGridViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_grid_item,  parent,false)

            val binding = MovieGridItemBinding.bind(view)

            return MovieGridViewHolder(
                binding
            )
        }
    }
}
