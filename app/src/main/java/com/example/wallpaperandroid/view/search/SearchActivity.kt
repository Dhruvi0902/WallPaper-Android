package com.example.wallpaperandroid.view.search

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wallpaperandroid.R
import com.example.wallpaperandroid.Utils
import com.example.wallpaperandroid.data.network.ResponseStatus
import com.example.wallpaperandroid.view.imagePreview.ImageActivity
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchActivity : AppCompatActivity() {

    private val searchActivityViewModel: SearchActivityViewModel by viewModel()
    private lateinit var searchListAdapter: SearchListAdapter
    private lateinit var searchCollectionListAdapter: SearchCollectionListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        if (intent.getStringExtra("screen")?.toString() == "List") {
            searchRecyclerView.visibility=View.VISIBLE
            searchCollectRecyclerView.visibility=View.GONE
            searchImages()
        } else {
            searchRecyclerView.visibility=View.GONE
            searchCollectRecyclerView.visibility=View.VISIBLE
            searchCollection()
        }
        searchActivityViewModel.responseStatusLiveData?.observe(this, Observer {
            when (it.status) {
                ResponseStatus.Status.LOADING -> searchProgressBar?.visibility = View.VISIBLE
                ResponseStatus.Status.SUCCESS -> searchProgressBar?.visibility = View.GONE
                ResponseStatus.Status.ERROR -> {
                    searchProgressBar?.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun searchCollection() {
        searchCollectRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        searchCollectionListAdapter = SearchCollectionListAdapter()
        searchCollectRecyclerView.adapter = searchCollectionListAdapter

        search.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchTextForTv.text=Html.fromHtml(resources.getString(R.string.search_for).plus(" ").plus("\"").plus("<i>"+search.text.toString()+"</i>").plus("\"."))
                callCollectionApi(search.text.toString())
                val inputManager =
                    this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(
                    this.currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
                return@OnEditorActionListener true
            }
            false
        })

        searchCollectionListAdapter.onItemClick = { _, v, data ->

            val intent = Intent(this, ImageActivity::class.java)
            intent.putExtra("image", data.coverPhoto?.urls?.full)
            intent.putExtra("searchCollectData", data)
            intent.putExtra("id", data.id)
            intent.putExtra("screen", 4)

            val options = ActivityOptions.makeCustomAnimation(
                this,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            startActivity(intent, options.toBundle())
        }
    }


    private fun searchImages() {
        searchRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        searchListAdapter = SearchListAdapter()
        searchRecyclerView.adapter = searchListAdapter

        search.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                callApi(search.text.toString())
                searchTextForTv.text=Html.fromHtml(resources.getString(R.string.search_for).plus(" ").plus("\"").plus("<i>"+search.text.toString()+"</i>").plus("\"."))
                val inputManager =
                    this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(
                    this.currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
                return@OnEditorActionListener true
            }
            false
        })

        searchListAdapter.onItemClick = { _, v, data ->

            val intent = Intent(this, ImageActivity::class.java)
            intent.putExtra("image", data.urls?.full)
            intent.putExtra("search", data)
            intent.putExtra("id", data.id)
            intent.putExtra("screen", 3)

            val options = ActivityOptions.makeCustomAnimation(
                this,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            startActivity(intent, options.toBundle())
        }
    }



    private fun callApi(search: String) {
            if (Utils.isConnected(this)) {
                searchActivityViewModel.getSearchedImages(search).observe(this, Observer {
                    searchListAdapter.submitList(it)
                    searchListAdapter.notifyDataSetChanged()

                })
            }
    }

    private fun callCollectionApi(search: String) {
        if (Utils.isConnected(this)) {
            searchActivityViewModel.getSearchedCollection(search).observe(this, Observer {
                searchCollectionListAdapter.submitList(it)
                searchCollectionListAdapter.notifyDataSetChanged()

            })
        }
    }
    }