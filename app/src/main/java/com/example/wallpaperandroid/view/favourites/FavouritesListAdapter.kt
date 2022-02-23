package com.example.wallpaperandroid.view.favourites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaperandroid.R
import com.example.wallpaperandroid.data.model.favourites.Favourites
import com.squareup.picasso.Picasso

class FavouritesListAdapter : PagedListAdapter<Favourites, FavouritesListAdapter.RowViewHolder>(REPO_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favourites_item_list, parent, false)
        return RowViewHolder(view)
    }

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        val rowPos = getItem(position)
        if (rowPos != null) {
            holder.bindView(rowPos)
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Favourites>() {
            override fun areItemsTheSame(oldItem: Favourites, newItem: Favourites): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Favourites, newItem: Favourites): Boolean =
                oldItem == newItem
        }
    }

    var onItemClick: ((pos: Int, view: View, data: Favourites) -> Unit)? = null


    inner class RowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.thumbnail)

        fun bindView(rowPos: Favourites) {
            Picasso
                .get()
                .load(rowPos.thumbnailUrl)
                .into(imageView)
            itemView.setOnClickListener { onItemClick?.invoke(adapterPosition, itemView, rowPos) }
        }
    }
}