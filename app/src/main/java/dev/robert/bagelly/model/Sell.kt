package dev.robert.bagelly.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sell(
    val itemUniqueId : String = "",
    val sellerId : String = "",
    val itemName : String? = "",
    val location: String? = "",
    val condition : String? = "",
    val description : String? = "",
    val price : String? = "",
    var category: String? = "",
    var subCategory: String? = "",
    var images : ArrayList<Uri>? = ArrayList(),
    var datePosted : String? = "",
    var image1 : String? = "",
    var image2 : String? = "",
    var image3 : String? = "",
) : Parcelable {
    constructor(category: String?, subCategory: String?, images: ArrayList<Uri>?) : this()
}

