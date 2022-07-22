package dev.robert.bagelly.ui.fragments.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.bagelly.data.repository.AuthenticationRepository
import dev.robert.bagelly.model.Users
import dev.robert.bagelly.utils.Resource
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject constructor(
    private val repository: AuthenticationRepository
    ) : ViewModel() {
        private val _auth = MutableLiveData<Resource<List<Users>>>()
        val auth : LiveData<Resource<List<Users>>> = _auth

        suspend fun createUser(users: Users) {
            _auth.value = Resource.Loading
            try{
                repository.saveUser(users){
                    _auth.value = it
                }
            }
            catch (e: Exception){
                _auth.value = Resource.Error(e.message.toString())
            }
        }



}