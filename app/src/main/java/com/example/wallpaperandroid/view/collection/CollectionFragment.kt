package com.example.wallpaperandroid.view.collection


import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wallpaperandroid.R
import com.example.wallpaperandroid.Utils
import com.example.wallpaperandroid.data.network.ResponseStatus
import com.example.wallpaperandroid.view.MainActivity
import com.example.wallpaperandroid.view.imagePreview.ImageActivity
import kotlinx.android.synthetic.main.collection_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class CollectionFragment : Fragment() {
    private val collectionViewModel: CollectionViewModel by viewModel()
    private lateinit var collectionListAdapter: CollectionListAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.collection_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        callApi()

        collectionRecyclerView.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        collectionListAdapter = CollectionListAdapter()
        collectionRecyclerView.adapter = collectionListAdapter

        collectionListAdapter.onItemClick = { _, _, data,id ->
            val intent = Intent(context, ImageActivity::class.java)
            intent.putExtra("image", data.full)
            intent.putExtra("screen", 2)
            intent.putExtra("thumb",data.thumb)
            intent.putExtra("id",id)

            val options = ActivityOptions.makeCustomAnimation(
                activity,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            startActivity(intent, options.toBundle())

        }


    }


    private fun callApi() {
        collectionViewModel.responseStatusLiveData?.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResponseStatus.Status.LOADING -> collectionProgressBar.visibility = View.VISIBLE
                ResponseStatus.Status.SUCCESS -> collectionProgressBar.visibility = View.GONE
                ResponseStatus.Status.ERROR -> {
                    collectionProgressBar.visibility = View.GONE
                    Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })

        if (Utils.isConnected(context)) {
            collectionViewModel.getOnlineCollection().observe(viewLifecycleOwner, Observer {
                collectionListAdapter.submitList(it)
                collectionListAdapter.notifyDataSetChanged()

            })
        } else {
            collectionViewModel.getCollection().observe(viewLifecycleOwner, Observer {
                collectionListAdapter.submitList(it)
                collectionListAdapter.notifyDataSetChanged()
                Log.d("MainActivity", "The list is" + it?.size)

            })
        }

    }

}
