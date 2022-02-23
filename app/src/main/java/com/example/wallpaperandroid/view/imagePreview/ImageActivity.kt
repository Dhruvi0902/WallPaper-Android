package com.example.wallpaperandroid.view.imagePreview

import android.Manifest
import android.app.Activity
import android.app.WallpaperManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.wallpaperandroid.R
import com.example.wallpaperandroid.data.model.SplashResponse
import com.example.wallpaperandroid.data.model.collection.CollectionResponse
import com.example.wallpaperandroid.data.model.search.ResultsItem
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_image.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException
import kotlin.math.max

class ImageActivity : AppCompatActivity(), View.OnClickListener {

    private var bitmapSet: Bitmap? = null
    private var isPermissionGranted: Boolean? = null
    private  var uri: String? =null
    private var bitmapImg: Bitmap? = null
    private var imageLoader: ImageLoader? = null
    private val imageViewModel: ImageViewModel by viewModel()
    private var isFav: Boolean? = null
    private val permissions = Manifest.permission.WRITE_EXTERNAL_STORAGE
    private var image: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        if(intent.getIntExtra("screen",0)==4){
            imageViewModel.getFavouriteByImage(intent.getStringExtra("image"))
        } else {
            imageViewModel.getFavourite(intent.getStringExtra("id"))
        }
        imageLoader = ImageLoader.getInstance()
        image = intent.getStringExtra("image")
        imageView.apply {
            transitionName = intent.getStringExtra("transitionName")
            loadImage(intent.getStringExtra("image")!!)
        }

        imageViewModel.favData.observe(this, Observer {
            if(it!=null){
                isFav=true
                favIcon.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.fav))
            } else{
                isFav= false
                favIcon.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.do_fav))
            }
        })

        setWallpaperIcon.setOnClickListener(this)
        downloadIcon.setOnClickListener(this)
        favIcon.setOnClickListener(this)
        cropIcon.setOnClickListener(this)
        backButton.setOnClickListener(this)
    }


    private fun loadImage(uriString: String) {
        imageLoader?.displayImage(
            uriString,
            imageView,
            object : ImageLoadingListener {
                override fun onLoadingStarted(imageUri: String, view: View) {
                    imagePreviewProgressBar?.visibility = View.VISIBLE
                    imgPlaceHolder?.visibility = View.VISIBLE
                }

                override fun onLoadingFailed(imageUri: String, view: View, failReason: FailReason) {
                    imgPlaceHolder?.visibility = View.VISIBLE
                    imagePreviewProgressBar?.visibility = View.GONE
                }

                override fun onLoadingComplete(imageUri: String, view: View, loadedImage: Bitmap) {
                    uri = imageUri
                    Log.d("ImageViewActivity", "the image uri is$uri")
                    bitmapImg = loadedImage
                    imgPlaceHolder?.visibility = View.GONE
                    imagePreviewProgressBar?.visibility = View.GONE
                    imageView.setImageBitmap(loadedImage)
                }

                override fun onLoadingCancelled(imageUri: String, view: View) {
                    imgPlaceHolder?.visibility = View.VISIBLE
                    imagePreviewProgressBar?.visibility = View.GONE
                }
            })

    }

    private fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri {
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Wallpaper", null)
        return Uri.parse(path.toString())
    }

    private fun getPermission() {
        Dexter.withContext(this)
            .withPermission(permissions)
            .withListener(object :PermissionListener{
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    isPermissionGranted=true
                }

                override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {
                    p1?.continuePermissionRequest()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    if(p0!!.isPermanentlyDenied){
                        showSettingsDialog()
                    }else {
                        p0.requestedPermission
                    }
                }
            }).check()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                loadImage(resultUri.toString())
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                Toast.makeText(this, "The error is:$error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showSettingsDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Need Permissions")
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton("GOTO SETTINGS") { dialog, _ ->
            dialog.cancel()
            openSettings()
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    // navigating user to app settings
    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }

    private fun saveImage(bitmap: Bitmap) {
        getImageUriFromBitmap(this,bitmap)
        Toast.makeText(this, "Image saved on device successfully", Toast.LENGTH_SHORT).show()
    }


    override fun onStop() {
        super.onStop()
        imageLoader?.clearDiskCache()
        imageLoader?.clearMemoryCache()
    }

    private fun centerCropForWallPaper(originalImage: Bitmap): Bitmap? {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val height = metrics.heightPixels
        val width = metrics.widthPixels
        val sourceWidth: Int = originalImage.width
        val sourceHeight: Int = originalImage.height
        val xScale: Float = width.toFloat() / sourceWidth
        val yScale: Float = height.toFloat() / sourceHeight
        val scale = max(xScale, yScale)

        // Now get the size of the source bitmap when scaled
        val scaledWidth: Float = scale * sourceWidth
        val scaledHeight: Float = scale * sourceHeight

        val left: Float = (width - scaledWidth) / 2
        val top: Float = (height - scaledHeight) / 2

        val targetRect = RectF(
            left, top, left + scaledWidth, top
                    + scaledHeight
        ) //from ww w  .j a va 2s. co m


        val dest = Bitmap.createBitmap(
            width, height,
            originalImage.config
        )
        val canvas = Canvas(dest)
        canvas.drawBitmap(originalImage, null, targetRect, null)

        return dest
    }

    private fun uriToBitmap(selectedFileUri: Uri):Bitmap {
        return MediaStore.Images.Media.getBitmap(this.contentResolver, selectedFileUri)
    }

    private fun setWallPaper() {
        bitmapSet = centerCropForWallPaper(uriToBitmap(getImageUriFromBitmap(this,bitmapImg!!)))
        val myWallpaperManager = WallpaperManager.getInstance(this)
        try {
            myWallpaperManager.setBitmap(bitmapSet)
            Toast.makeText(this, "Wallpaper set successfully", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun setLockScreen() {
        bitmapSet = centerCropForWallPaper(uriToBitmap(getImageUriFromBitmap(this,bitmapImg!!)))
        val myWallpaperManager = WallpaperManager.getInstance(this)
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                myWallpaperManager.setBitmap(bitmapSet, null, true, WallpaperManager.FLAG_LOCK)
            }
            Toast.makeText(this, "Lock screen set successfully", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun setBothLockAndWallpaper() {
        bitmapSet = centerCropForWallPaper(uriToBitmap(getImageUriFromBitmap(this,bitmapImg!!)))
        val myWallpaperManager = WallpaperManager.getInstance(this)
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                myWallpaperManager.setBitmap(bitmapSet, null, true, WallpaperManager.FLAG_LOCK)
            }
            myWallpaperManager.setBitmap(bitmapSet)
            Toast.makeText(this, "Lockscreen and Wallpaper set successfully", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.setWallpaperIcon -> {
                val popup = PopupMenu(this, setWallpaperIcon)
                popup.menuInflater.inflate(R.menu.wallpaper_menu, popup.menu)

                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.wallpaper -> setWallPaper()
                        R.id.lockScreen -> setLockScreen()
                        R.id.both -> setBothLockAndWallpaper()
                        else -> setWallPaper()
                    }
                    true
                }

                popup.show()

            }
            R.id.downloadIcon -> {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    saveImage(bitmapImg!!)
                } else {
                    getPermission()
                }

            }
            R.id.favIcon -> {
                if (isFav!!) {
                    isFav=false
                    favIcon.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.do_fav))
                } else {
                    isFav=true
                    favIcon.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.fav))
                }
                updateFavourites()
            }
            R.id.cropIcon -> {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                    CropImage.activity(getImageUriFromBitmap(this, bitmapImg!!)).start(this)
                } else {
                    getPermission()
                }
            }
            R.id.backButton -> {
               onBackPressed()
            }
        }
    }

    private fun updateFavourites() {
        when(intent.getIntExtra("screen",0)){
            1->{
                imageViewModel.updateFavExplore(isFav!!, intent.getSerializableExtra("data") as SplashResponse)
            }
            2->{
                imageViewModel.updateFavCollect(isFav!!, intent.getStringExtra("id")!!,intent.getStringExtra("thumb")!!,intent.getStringExtra("image")!!)
            }
            3->{
                imageViewModel.updateFavSearch(isFav!!,intent.getSerializableExtra("search") as ResultsItem)
            }
            4->{
                imageViewModel.updateFavSearchCollect(isFav!!,intent.getStringExtra("id"),intent.getSerializableExtra("searchCollectData") as com.example.wallpaperandroid.data.model.searchCollection.ResultsItem)
            }
            5->{
                imageViewModel.updateFavFeatCollect(isFav!!, intent.getSerializableExtra("featCollect")!! as CollectionResponse)
            }
            6->{
                imageViewModel.deleteFav(intent.getStringExtra("id"))
            }
        }
        Toast.makeText(this, "Updated Favourites item", Toast.LENGTH_SHORT).show()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
    }
}