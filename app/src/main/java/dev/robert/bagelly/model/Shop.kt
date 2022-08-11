package dev.robert.bagelly.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Shop(
    var ownerId : String? = "",
    var shopId: String? ="",
    var shopName : String? = "",
    var shopCategory : String? = "",
    var shopDescription : String? = "",
    var shopWebsite : String? = "",
    var shopPhone : String? = "",
    var shopLocation: String? = "",
    var shopImage : String? = "",
) : Parcelable
