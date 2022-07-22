package dev.robert.bagelly.model

import android.location.Location

data class Sell(
    val id : String? = "",
    val itemName : String? = "",
    val location: String? = "",
    val condition : String? = "",
    val description : String? = "",
    val price : String? = "",
    val category: SellCategory
)
