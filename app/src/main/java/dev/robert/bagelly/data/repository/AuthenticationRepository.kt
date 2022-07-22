package dev.robert.bagelly.data.repository

import dev.robert.bagelly.model.Users
import dev.robert.bagelly.utils.Resource

interface AuthenticationRepository {
  suspend fun saveUser(user: Users, result: (Resource<List<Users>>) -> Unit)
  //suspend fun login(user: Users, result: (Resource<List<Users>>) -> Unit)
}