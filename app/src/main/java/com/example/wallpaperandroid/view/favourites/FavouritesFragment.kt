package com.example.wallpaperandroid.view.favourites

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.example.wallpaperandroid.R
import com.example.wallpaperandroid.view.imagePreview.ImageActivity
import kotlinx.android.synthetic.main.favourites_fragment.*
import kotlinx.android.synthetic.main.list_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouritesFragment : Fragment() {

   private val favouritesViewModel : FavouritesViewModel by viewModel()
    private lateinit var favouritesListAdapter: FavouritesListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.favourites_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()

       favRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        favouritesListAdapter = FavouritesListAdapter()
        favRecyclerView.adapter = favouritesListAdapter
        favouritesListAdapter.onItemClick = { _, v, data ->
            val intent= Intent(context, ImageActivity::class.java)
            intent.putExtra("image", data.fullUrl)
            intent.putExtra("isFav", true)
            intent.putExtra("transitionName",v.transitionName)
            intent.putExtra("id", data.id)
            intent.putExtra("screen", 6)

            val options = ActivityOptions.makeCustomAnimation(activity, R.anim.slide_in_left,R.anim.slide_out_right)
            startActivity(intent, options.toBundle())
        }

    }

    private fun getData() {
        favouritesViewModel.imagesLiveData.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){
                no_data_anim.visibility=View.GONE
                noDataTv.visibility=View.GONE
                no_data_anim.pauseAnimation()
                favouritesListAdapter.submitList(it)
                favouritesListAdapter.notifyDataSetChanged()
            }
            else{
                no_data_anim.visibility=View.VISIBLE
                noDataTv.visibility=View.VISIBLE
                no_data_anim.playAnimation()
                favouritesListAdapter.submitList(it)
                favouritesListAdapter.notifyDataSetChanged()
            }
        })
    }

}
