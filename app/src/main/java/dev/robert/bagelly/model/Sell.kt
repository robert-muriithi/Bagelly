package dev.robert.bagelly.model

data class Sell(
    val itemName : String? = "",
    val location: String? = "",
    val condition : String? = "",
    val description : String? = "",
    val price : String? = "",
    val category: SellCategory
)
