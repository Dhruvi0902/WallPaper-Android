package com.example.wallpaperandroid.view.ui.featuredCollection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaperandroid.R
import com.example.wallpaperandroid.Utils
import com.example.wallpaperandroid.data.model.collection.CollectionResponse
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso


class FeaturedCollectionListAdapter :
    PagedListAdapter<CollectionResponse, FeaturedCollectionListAdapter.RowViewHolder>(REPO_COMPARATOR) {


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
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<CollectionResponse>() {
            override fun areItemsTheSame(
                oldItem: CollectionResponse,
                newItem: CollectionResponse
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: CollectionResponse,
                newItem: CollectionResponse
            ): Boolean =
                oldItem == newItem
        }
    }

    var onItemClick: ((pos: Int, view: ImageView, data: CollectionResponse) -> Unit)? = null


    inner class RowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.thumbnailSingleList)

        fun bindView(rowPos: CollectionResponse) {
            imageView.apply {
                    Picasso.get()
                        .load(rowPos.coverPhoto?.urls?.thumb)
                        .into(this)

                ViewCompat.setTransitionName(imageView, "image".plus(adapterPosition))
            }


            itemView.setOnClickListener { onItemClick?.invoke(adapterPosition, imageView, rowPos) }
        }


    }
}