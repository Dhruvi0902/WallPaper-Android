package com.example.wallpaperandroid.view.ui.featuredCollection

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wallpaperandroid.R
import com.example.wallpaperandroid.data.network.ResponseStatus
import com.example.wallpaperandroid.view.imagePreview.ImageActivity
import kotlinx.android.synthetic.main.featured_collection_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeaturedCollectionFragment : Fragment() {

    private val viewModel: FeaturedCollectionViewModel by viewModel()
    private lateinit var featuredCollectionListAdapter: FeaturedCollectionListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.featured_collection_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callApi()
        featCollectRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        featuredCollectionListAdapter = FeaturedCollectionListAdapter()
        featCollectRecyclerView.adapter=featuredCollectionListAdapter
        featuredCollectionListAdapter.onItemClick = { _, v, data ->

            val intent= Intent(context, ImageActivity::class.java)
            intent.putExtra("image", data.coverPhoto?.urls?.full)
            intent.putExtra("featCollect", data)
            intent.putExtra("transitionName",v.transitionName)
            intent.putExtra("id", data.id)
            intent.putExtra("screen", 5)

            val options = ActivityOptions.makeCustomAnimation(activity, R.anim.slide_in_left,R.anim.slide_out_right)
            startActivity(intent, options.toBundle())
        }
    }

    private fun callApi() {
        viewModel.responseStatusLiveData?.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResponseStatus.Status.LOADING -> featCollectPb?.visibility = View.VISIBLE
                ResponseStatus.Status.SUCCESS -> featCollectPb?.visibility = View.GONE
                ResponseStatus.Status.ERROR -> {
                    featCollectPb.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
            viewModel.getOnlineCollection().observe(viewLifecycleOwner, Observer {
                featuredCollectionListAdapter.submitList(it)
                featuredCollectionListAdapter.notifyDataSetChanged()

            })

    }


}