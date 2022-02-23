package com.example.wallpaperandroid.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.wallpaperandroid.R
import com.example.wallpaperandroid.view.collection.CollectionFragment
import com.example.wallpaperandroid.view.favourites.FavouritesFragment
import com.example.wallpaperandroid.view.singleList.ExploreFragment
import com.example.wallpaperandroid.view.viewPagerTransformer.HorizontalFlipTransformation
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.app_bar_main.view.*
import kotlinx.android.synthetic.main.collection_custom_tab.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.favourites_custom_tab.*
import kotlinx.android.synthetic.main.fragment_view_pager.*
import kotlinx.android.synthetic.main.home_custom_tab.*


class ViewPagerFragment : Fragment() {

    val tagArray = listOf("Feature", "Collection", "Favourites")
    private var adapter: ViewPagerFragmentAdapter?= null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ViewPagerFragmentAdapter(this)
        viewPager.adapter=adapter
        viewPager.offscreenPageLimit = 2
        viewPager.setPageTransformer(HorizontalFlipTransformation())

        // attaching tab mediator
        TabLayoutMediator(tab, viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                when (position) {
                    0 -> {
                        tab.customView= LayoutInflater.from(activity).inflate(R.layout.home_custom_tab, null) as LinearLayout?
                    }
                    1 -> {
                        val view=LayoutInflater.from(activity).inflate(R.layout.collection_custom_tab, null) as LinearLayout?
                        tab.customView= view
                    }
                    2 -> {
                        val view=LayoutInflater.from(activity).inflate(R.layout.favourites_custom_tab, null) as LinearLayout?
                        tab.customView= view
                    }
                }
            }
        ).attach()

        viewPager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                when(position){
                    0->{
                        (activity as MainActivity).setToolbarTitle(resources.getString(R.string.feature_list))
                        (activity as MainActivity).searchString("List")
                        homeNameTv.visibility=View.VISIBLE
                        collectionNameTv.visibility=View.GONE
                        favNameTv.visibility=View.GONE
                    }
                    1->{
                        (activity as MainActivity).setToolbarTitle(resources.getString(R.string.collection_list))
                        (activity as MainActivity).searchString("Collect")
                        homeNameTv.visibility=View.GONE
                        collectionNameTv.visibility=View.VISIBLE
                        favNameTv.visibility=View.GONE
                    }
                    2->{
                        (activity as MainActivity).setToolbarTitle(resources.getString(R.string.fav_list))
                        (activity as MainActivity).searchString("Fav")
                        homeNameTv.visibility=View.GONE
                        collectionNameTv.visibility=View.GONE
                        favNameTv.visibility=View.VISIBLE
                    }
                }
            }
        })

    }



    inner class ViewPagerFragmentAdapter(fragment: Fragment) :
        FragmentStateAdapter(fragment) {


        override fun getItemCount(): Int {
            return tagArray.size
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> ExploreFragment()
                1 -> CollectionFragment()
                2 -> FavouritesFragment()
                else -> ExploreFragment()
            }
        }
    }

}