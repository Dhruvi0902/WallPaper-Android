package com.example.wallpaperandroid

import android.app.Application
import com.example.wallpaperandroid.framework.RetrofitService.networkModuleDi
import com.example.wallpaperandroid.framework.databaseModule
import com.example.wallpaperandroid.framework.viewModelModuleDI
import com.facebook.stetho.Stetho
import com.google.firebase.analytics.FirebaseAnalytics
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.utils.StorageUtils
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class WallPaperApplication: Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: WallPaperApplication? = null

        fun applicationContext(): WallPaperApplication {
            return instance as WallPaperApplication
        }
    }
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@WallPaperApplication)
            modules(
                listOf(
                    networkModuleDi,
                    viewModelModuleDI,
                    databaseModule
                )
            )
        }
        FirebaseAnalytics.getInstance(this)
        Stetho.initializeWithDefaults(this)
        val cacheDir = StorageUtils.getOwnCacheDirectory(applicationContext, "highRes")
        val config = ImageLoaderConfiguration.Builder(applicationContext)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY-1)
                .memoryCache(LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCache(UnlimitedDiskCache(cacheDir)) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .build()
        ImageLoader.getInstance().init(config)
    }
}