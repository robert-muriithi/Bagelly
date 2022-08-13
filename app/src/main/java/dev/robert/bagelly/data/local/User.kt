package dev.robert.bagelly.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int? ,
    var name: String? = "",
    var email : String? = "",
    var phoneNumber : String? = "",
    var profileImageUrl : String? = "",
    var location : String? = "",
)
