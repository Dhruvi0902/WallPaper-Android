package com.example.wallpaperandroid.data.model.faq

import android.os.Parcelable
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ans(
     var answer:String?=null
):Parcelable

data class Faq(
     var question:String?=null,
     var answer:List<Ans>
):ExpandableGroup<Ans>(question,answer)