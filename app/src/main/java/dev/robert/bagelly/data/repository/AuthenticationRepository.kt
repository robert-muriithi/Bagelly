package dev.robert.bagelly.data.repository

import com.google.firebase.auth.AuthResult
import dev.robert.bagelly.model.Users
import dev.robert.bagelly.utils.Resource

interface AuthenticationRepository {
    suspend fun registerUser(email: String, password: String, users: Users, result :(Resource<String>) -> Unit)
    suspend fun loginUser(email: String, password: String, result :(Resource<String>) -> Unit)
    suspend fun updateUser(users: Users, result: (Resource<String>) -> Unit)
    suspend fun forgotPassword(email: String, result: (Resource<String>) -> Unit)
    suspend fun logout(result: () -> Unit)
}