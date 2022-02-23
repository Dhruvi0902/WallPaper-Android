package com.example.wallpaperandroid.view.collection

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaperandroid.R
import com.example.wallpaperandroid.data.model.Urls
import com.example.wallpaperandroid.data.model.collection.CollectionResponse
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception

class CollectionListAdapter : PagedListAdapter<CollectionResponse, CollectionListAdapter.RowViewHolder>(REPO_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.collection_item_list, parent, false)
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
            override fun areItemsTheSame(oldItem: CollectionResponse, newItem: CollectionResponse): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CollectionResponse, newItem: CollectionResponse): Boolean =
                oldItem == newItem
        }
    }

    var onItemClick: ((pos: Int, view: View, data: Urls,id:String) -> Unit)? = null

    inner class RowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.thumbnailCollectList)
        private val titleTv:TextView = itemView.findViewById(R.id.collectionTitle)
        private val imageOne:ImageView = itemView.findViewById(R.id.imageOne)
        private val imageTwo:ImageView = itemView.findViewById(R.id.imageTwo)

        fun bindView(rowPos: CollectionResponse) {
            Picasso
                .get()
                .load(rowPos.coverPhoto?.urls?.thumb)
                .into(object :Target{
                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                        //no need
                    }

                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                        //no need
                    }

                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        imageView.setImageBitmap(bitmap)
                        Picasso.get().load(rowPos.previewPhotos?.get(1)?.urls?.thumb).into(imageOne)
                        Picasso.get().load(rowPos.previewPhotos?.get(2)?.urls?.thumb).into(imageTwo)
                    }
                })


            titleTv.text=rowPos.title?.split(' ')?.joinToString(" ") { it.capitalize() }

            imageView.setOnClickListener { onItemClick?.invoke(adapterPosition, itemView, rowPos.previewPhotos?.get(0)?.urls!!,rowPos.id.toString()) }
            imageOne.setOnClickListener { onItemClick?.invoke(adapterPosition, itemView, rowPos.previewPhotos?.get(1)?.urls!!,rowPos.id.toString()) }
            imageTwo.setOnClickListener { onItemClick?.invoke(adapterPosition, itemView, rowPos.previewPhotos?.get(2)?.urls!!,rowPos.id.toString()) }
        }
    }
}