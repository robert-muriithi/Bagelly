package dev.robert.bagelly.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sell(
    var itemUniqueId : String = "",
    var sellerId : String = "",
    var itemName : String? = "",
    var location: String? = "",
    var condition : String? = "",
    var description : String? = "",
    var price : String? = "",
    var category: String? = "",
    var subCategory: String? = "",
    var images : ArrayList<Uri>? = ArrayList(),
    var datePosted : String? = "",
    var image1 : String? = "",
    var image2 : String? = "",
    var image3 : String? = "",
) : Parcelable {
    constructor(category: String?, subCategory: String?, images: ArrayList<Uri>? /* = java.util.ArrayList<android.net.Uri>? */) : this() {
        this.category = category
        this.subCategory = subCategory
        this.images = images
    }
    constructor (itemUniqueId: String, sellerId: String, itemName: String, location: String, condition: String, description: String, price: String, category: String, subCategory: String, datePosted: String, image1: String, image2: String, image3: String) : this() {
        this.itemUniqueId = itemUniqueId
        this.sellerId = sellerId
        this.itemName = itemName
        this.location = location
        this.condition = condition
        this.description = description
        this.price = price
        this.category = category
        this.subCategory = subCategory
        this.datePosted = datePosted
        this.image1 = image1
        this.image2 = image2
        this.image3 = image3
    }
}

