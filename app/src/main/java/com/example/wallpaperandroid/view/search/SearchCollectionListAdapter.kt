package com.example.wallpaperandroid.view.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaperandroid.R
import com.example.wallpaperandroid.data.model.searchCollection.ResultsItem
import com.squareup.picasso.Picasso


class SearchCollectionListAdapter : PagedListAdapter<ResultsItem, SearchCollectionListAdapter.RowViewHolder>(REPO_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_list_item, parent, false)
        return RowViewHolder(view)
    }

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        val rowPos = getItem(position)
        if (rowPos != null) {
            holder.bindView(rowPos)
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<ResultsItem>() {
            override fun areItemsTheSame(
                oldItem: ResultsItem,
                newItem: ResultsItem
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ResultsItem,
                newItem: ResultsItem
            ): Boolean =
                oldItem == newItem
        }
    }

    var onItemClick: ((pos: Int, view: ImageView, data: ResultsItem) -> Unit)? = null
    var onItemLongClick: ((pos: Int, view: View, data: ResultsItem) -> Unit)? = null


    inner class RowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.thumbnailSingleList)

        fun bindView(rowPos: ResultsItem) {
            imageView.apply {
                    Picasso.get()
                        .load(rowPos.coverPhoto?.urls?.thumb)
                        .into(this)
                ViewCompat.setTransitionName(imageView, "image".plus(adapterPosition))
            }


            itemView.setOnClickListener { onItemClick?.invoke(adapterPosition, imageView, rowPos) }
            itemView.setOnLongClickListener {
                onItemLongClick?.invoke(adapterPosition, itemView, rowPos)
                true
            }
        }


    }
}