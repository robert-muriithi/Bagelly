package dev.robert.bagelly.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    var postId : String? = "",
    var ownerId : String? = "",
    var postImage : String? = "",
    var postName : String? = "",
    var postType: String? = "",
    var postCondition: String? = "",
    var postDescription : String? = "",
    var postSpecificLoc : String? = "",
    var postItemPrice : String? = "",
) : Parcelable
