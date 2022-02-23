package com.example.wallpaperandroid.view.ui.help

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wallpaperandroid.R
import com.example.wallpaperandroid.data.model.faq.Ans
import com.example.wallpaperandroid.data.model.faq.Faq
import kotlinx.android.synthetic.main.fragment_help_and_faq.*


class HelpAndFaqFragment : Fragment() {

    val arrayList = ArrayList<Faq>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help_and_faq, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arrayList.add(Faq("What is app all about?", listOf(Ans("The Wallpaper app is an android based application for viewing number of beautiful pictures and collection, also allows user to set picture as favourites also"))))
        arrayList.add(Faq("What features does app have",listOf(Ans("This app allows user to set any image as wallpaper or home screen, also allows to crop the image, to download image and also to save as user's favourites"))))
        arrayList.add(Faq("Are images downloadable?",listOf(Ans("Yes, user can download image."))))
        arrayList.add(Faq("Where the downloaded images are stored?",listOf(Ans("The images are stored in pictures folder in gallery package"))))
        arrayList.add(Faq("Why images set as wallpaper are cropped",listOf(Ans("As the images which are having greater dimension then your device will be cropped and which is smaller will be shown as it it."))))
        arrayList.add(Faq("Does this app support offline?",listOf(Ans("Yes this app support offline loading images but will show images which were loaded before"))))
        arrayList.add(Faq("What are collection?",listOf(Ans("The collection section shows the images categorized wise"))))


        val adapter = HelpAndFaqListAdapter(arrayList)
        helpRv.apply {
            layoutManager = LinearLayoutManager(context)
            helpRv.adapter = adapter
        }

        emailFb.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_APP_EMAIL)
            startActivity(intent)
            startActivity(Intent.createChooser(intent, getString(R.string.choose_email)))
        }
    }

    }

