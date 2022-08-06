package dev.robert.bagelly.model


data class Shop(
    var ownerId : String? = "",
    var shopName : String? = "",
    var shopCategory : String? = "",
    var shopDescription : String? = "",
    var shopWebsite : String? = "",
    var shopPhone : String? = "",
    var shopLocation: String? = "",
    var shopImage : String? = "",
)
