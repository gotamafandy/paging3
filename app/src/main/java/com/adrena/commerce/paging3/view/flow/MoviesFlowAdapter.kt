package com.adrena.commerce.paging3.view.flow

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.adrena.commerce.paging3.R
import com.adrena.commerce.paging3.view.model.UiModel
import com.adrena.commerce.paging3.view.viewholder.MovieViewHolder
import com.adrena.commerce.paging3.view.viewholder.SeparatorViewHolder
import java.util.*

class MoviesFlowAdapter(private val locale: Locale) : PagingDataAdapter<UiModel, RecyclerView.ViewHolder>(
    COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.movie_item) {
            MovieViewHolder.create(
                parent,
                locale
            )
        } else {
            SeparatorViewHolder.create(parent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UiModel.MovieItem -> R.layout.movie_item
            is UiModel.SeparatorItem -> R.layout.separator_item
            null -> throw UnsupportedOperationException("Unknown View")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            when (it) {
                is UiModel.MovieItem -> (holder as MovieViewHolder).bind(it.movie)
                is UiModel.SeparatorItem -> (holder as SeparatorViewHolder).bind(it.description)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<UiModel>() {
            override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
                return (oldItem is UiModel.MovieItem && newItem is UiModel.MovieItem && oldItem.movie.movieId == newItem.movie.movieId) ||
                        (oldItem is UiModel.SeparatorItem && newItem is UiModel.SeparatorItem && oldItem.description == newItem.description)
            }

            override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
