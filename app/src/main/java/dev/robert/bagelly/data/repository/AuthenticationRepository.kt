package dev.robert.bagelly.data.repository

import com.google.firebase.auth.AuthResult
import dev.robert.bagelly.model.Users
import dev.robert.bagelly.utils.Resource

interface AuthenticationRepository {
    suspend fun register(name : String, email : String, phoneNothing: String, password : String) : Resource<AuthResult>
    suspend fun login(email: String, password: String): Resource<AuthResult>
    suspend fun forgotPassword(email: String): Resource<Any>
}