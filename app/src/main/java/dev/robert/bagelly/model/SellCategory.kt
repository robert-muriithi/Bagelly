package dev.robert.bagelly.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SellCategory(
    var category: String? = "",
    var subCategory: String? = "",
    var images : ArrayList<Uri>? = ArrayList()
) : Parcelable
