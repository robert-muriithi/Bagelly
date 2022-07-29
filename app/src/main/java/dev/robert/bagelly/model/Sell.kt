package dev.robert.bagelly.model

data class Sell(
    val sellerId : String = "",
    val itemName : String? = "",
    val location: String? = "",
    val condition : String? = "",
    val description : String? = "",
    val price : String? = "",
    val category: SellCategory
)
