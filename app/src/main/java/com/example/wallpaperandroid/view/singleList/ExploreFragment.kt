package com.example.wallpaperandroid.view.singleList

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wallpaperandroid.R
import com.example.wallpaperandroid.Utils
import com.example.wallpaperandroid.data.network.ResponseStatus
import com.example.wallpaperandroid.view.MainActivity
import com.example.wallpaperandroid.view.imagePreview.ImageActivity
import com.example.wallpaperandroid.view.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.list_fragment.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent


class ExploreFragment : Fragment(), KoinComponent {

    private val listViewModel: ListViewModel by viewModel()
    private lateinit var singleListAdapter: SingleListAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.list_fragment, container, false)
        callApi()
        view.recycler_view.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        singleListAdapter = SingleListAdapter()
        view.recycler_view.adapter=singleListAdapter
        singleListAdapter.onItemClick = { _, v, data ->

            val intent= Intent(context, ImageActivity::class.java)
            intent.putExtra("image", data.urls?.full)
            intent.putExtra("data", data)
            intent.putExtra("transitionName",v.transitionName)
            intent.putExtra("id", data.id)
            intent.putExtra("screen", 1)

            val options = ActivityOptions.makeCustomAnimation(activity, R.anim.slide_in_left,R.anim.slide_out_right)
            startActivity(intent, options.toBundle())
        }


        return view
    }


    private fun callApi() {
        listViewModel.responseStatusLiveData?.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResponseStatus.Status.LOADING -> view?.progress_bar?.visibility = View.VISIBLE
                ResponseStatus.Status.SUCCESS -> view?.progress_bar?.visibility = View.GONE
                ResponseStatus.Status.ERROR -> {
                    view?.progress_bar?.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        if (Utils.isConnected(context)) {
            listViewModel.getOnlineImages().observe(viewLifecycleOwner, Observer {
                singleListAdapter.submitList(it)
                singleListAdapter.notifyDataSetChanged()

            })
        } else {
            listViewModel.getImages().observe(viewLifecycleOwner, Observer {
                singleListAdapter.submitList(it)
                singleListAdapter.notifyDataSetChanged()

            })
        }
    }

}
