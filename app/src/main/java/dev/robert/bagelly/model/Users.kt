package dev.robert.bagelly.model

data class Users(
    var id: String? = null,
    var name: String? = "",
    var email : String? = "",
    var phoneNumber : String? = "",
    val password : String? = "",
    var profileImageUrl : String? = "",
    var location : String? = "",
){
    constructor(id: String?, name: String?, email: String?, phoneNumber: String?) : this(){
        this.id = id
        this.name = name
        this.email = email
        this.phoneNumber = phoneNumber
    }
    constructor(id: String?, name: String?, email: String?, phoneNumber: String?, profileImageUrl: String?, location: String?) : this(){
        this.id = id
        this.name = name
        this.email = email
        this.phoneNumber = phoneNumber
        this.profileImageUrl = profileImageUrl
        this.location = location
    }
    constructor(name: String?, email: String?, phoneNumber: String?, profileImageUrl: String?, location: String?) : this(){
        this.name = name
        this.email = email
        this.phoneNumber = phoneNumber
        this.profileImageUrl = profileImageUrl
        this.location = location
    }
}
