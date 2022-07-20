package dev.robert.bagelly.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SellCategory(
    var category: String? = "",
    var subCategory: String? = "",
    var picture1: String? = "",
    var picture2 : String? = "",
    var picture3 : String? = "",
    /*var itemName : String? = "",
    var location: String? = "",
    var condition: String? = "",
    var description: String? = "",
    var price: String? = "",*/
) : Parcelable
