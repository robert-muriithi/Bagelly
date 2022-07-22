package dev.robert.bagelly.model

import com.google.firebase.firestore.Exclude

data class Users(
    val id: String? = null,
    val name: String? = "",
    val email : String? = "",
    val phoneNumber : String? = "",
    @Exclude
    val password : String? = "",
)
