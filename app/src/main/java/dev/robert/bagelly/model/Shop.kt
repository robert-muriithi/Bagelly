package dev.robert.bagelly.model


data class Shop(
    val ownerId : String? = "",
    val shopName : String? = "",
    val shopCategory : String? = "",
    val shopDescription : String? = "",
    val shopWebsite : String? = "",
    val shopPhone : String? = "",
    val shopLocation: String? = "",
    val shopImage : String? = "",
)
