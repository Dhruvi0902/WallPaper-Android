package com.example.wallpaperandroid.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import com.example.wallpaperandroid.R
import com.example.wallpaperandroid.view.search.SearchActivity
import com.example.wallpaperandroid.view.ui.featuredCollection.FeaturedCollectionFragment
import com.example.wallpaperandroid.view.ui.help.HelpAndFaqFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.app_bar_main.view.*


class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private var menu:MenuItem? =null
    private var searchType:String? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        menu=nav_view.menu.getItem(2)

        appBarLayout?.toolbar?.searchIv?.setOnClickListener {
            if(searchType=="List") {
                val intent = Intent(this, SearchActivity::class.java)
                intent.putExtra("screen", "List")
                startActivity(intent)
            } else if(searchType=="Collect"){
                val intent = Intent(this, SearchActivity::class.java)
                intent.putExtra("screen", "Collect")
                startActivity(intent)
            }
        }

        appBarLayout?.toolbar?.menuIv?.setOnClickListener {
            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START)
            } else {
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }

        nav_view.setNavigationItemSelectedListener(this)
        nav_view.menu.getItem(0).isChecked = true
        nav_view.menu.performIdentifierAction(R.id.nav_explore, 0)
    }

    fun searchString(type:String){
        searchType=type
        when (type) {
            "List" -> {
                appBarLayout?.toolbar?.searchIv?.visibility=View.VISIBLE
            }
            "Collect" -> {
                appBarLayout?.toolbar?.searchIv?.visibility=View.VISIBLE
            }
            else -> {
                appBarLayout?.toolbar?.searchIv?.visibility=View.GONE
            }
        }
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }

    }

    fun setToolbarTitle(title:String){
        appBarLayout.toolbar?.actionTitleTv?.text=title
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_explore ->{
                appBarLayout?.toolbar?.searchIv?.visibility=View.VISIBLE
                val fragment=ViewPagerFragment()
                supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment,fragment).addToBackStack(null).commit()
            }
            R.id.nav_feature -> {
                appBarLayout?.toolbar?.searchIv?.visibility=View.GONE
                appBarLayout.toolbar?.actionTitleTv?.text=resources.getString(R.string.feat_collect)
                val fragment=FeaturedCollectionFragment()
                supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment,fragment,"feature").addToBackStack(null).commit()
            }
            R.id.nav_theme->{
                appBarLayout?.toolbar?.searchIv?.visibility=View.GONE
                when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                    Configuration.UI_MODE_NIGHT_YES -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                    Configuration.UI_MODE_NIGHT_NO -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                }

            }
            R.id.nav_rate_us->{
                appBarLayout?.toolbar?.searchIv?.visibility=View.GONE
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
                } catch (e: ActivityNotFoundException) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
                }

            }
            R.id.nav_help->{
                appBarLayout?.toolbar?.searchIv?.visibility=View.GONE
                appBarLayout.toolbar?.actionTitleTv?.text=resources.getString(R.string.help)
                val fragment=HelpAndFaqFragment()
                supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment,fragment).addToBackStack(null).commit()
            }
        }
        item.isChecked = true
        drawer_layout.closeDrawers()
        return true
    }
}
