package dev.robert.bagelly.ui.fragments.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.bagelly.data.repository.AuthenticationRepository
import dev.robert.bagelly.model.Users
import dev.robert.bagelly.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject constructor(
    private val repository: AuthenticationRepository
) : ViewModel() {

    private val _register = MutableLiveData<Resource<String>>()
    val register: LiveData<Resource<String>> = _register

    private val _login = MutableLiveData<Resource<String>>()
    val login: LiveData<Resource<String>> = _login

    private val forgotPassword = MutableLiveData<Resource<String>>()
    val _forgotPassword: LiveData<Resource<String>> = forgotPassword

    fun registerUser(email: String, password: String, users: Users) {
       viewModelScope.launch {
           _register.value = Resource.Loading
           try {
               val result = repository.registerUser(email, password, users) {
                   _register.value = it
               }
               Resource.Success(result)
           }
           catch (e : Exception){
               _register.value = Resource.Error(e.message.toString())
           }
           catch (e: FirebaseException){
               _register.value = Resource.Error(e.message.toString())
           }
       }
    }

    suspend fun login(email: String, password: String) {
        _login.value = Resource.Loading
        try {
            val result = repository.loginUser(email, password){
                _login.value = it
            }
            Resource.Success(result)
        } catch (e: Exception) {
            _login.value = Resource.Error(e.message.toString())
        }
        catch (e : FirebaseAuthInvalidCredentialsException){
            _login.value = Resource.Error(e.message.toString())
        }
    }

    suspend fun forgotPassword(email: String) {
        forgotPassword.value = Resource.Loading
        try {
            repository.forgotPassword(email) {
                forgotPassword.value = it
            }
        } catch (e: Exception) {
            forgotPassword.value = Resource.Error(e.message.toString())
        }
    }
}