package dev.robert.bagelly.model

import com.google.firebase.firestore.Exclude

data class Users(
    var id: String? = null,
    var name: String? = "",
    var email : String? = "",
    var phoneNumber : String? = "",
    val password : String? = "",
){
    constructor(id: String?, name: String?, email: String?, phoneNumber: String?) : this(){
        this.id = id
        this.name = name
        this.email = email
        this.phoneNumber = phoneNumber
    }
}
