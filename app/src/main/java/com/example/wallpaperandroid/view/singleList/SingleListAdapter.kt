package com.example.wallpaperandroid.view.singleList

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
import com.example.wallpaperandroid.data.model.SplashResponse
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso


class SingleListAdapter :
    PagedListAdapter<SplashResponse, SingleListAdapter.RowViewHolder>(REPO_COMPARATOR) {


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
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<SplashResponse>() {
            override fun areItemsTheSame(
                oldItem: SplashResponse,
                newItem: SplashResponse
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: SplashResponse,
                newItem: SplashResponse
            ): Boolean =
                oldItem == newItem
        }
    }

    var onItemClick: ((pos: Int, view: ImageView, data: SplashResponse) -> Unit)? = null


    inner class RowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.thumbnailSingleList)

        fun bindView(rowPos: SplashResponse) {
            imageView.apply {
                if(Utils.isConnected(context)) {
                    Picasso.get()
                        .load(rowPos.urls?.thumb)
                        .into(this)
                }else{
                    Picasso.get().load(rowPos.urls?.thumb).networkPolicy(NetworkPolicy.OFFLINE)
                        .into(this)
                }
                ViewCompat.setTransitionName(imageView, "image".plus(adapterPosition))
            }


            itemView.setOnClickListener { onItemClick?.invoke(adapterPosition, imageView, rowPos) }
        }


    }
}