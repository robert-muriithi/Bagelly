package dev.robert.bagelly.ui.fragments.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.bagelly.data.repository.AuthenticationRepository
import dev.robert.bagelly.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject constructor(
    private val repository: AuthenticationRepository
    ) : ViewModel() {

        private val _register = MutableLiveData<Resource<AuthResult>>()
        val register : LiveData<Resource<AuthResult>> = _register

        suspend fun register(name: String, email: String, phoneNumber: String, password: String){
            _register.value = Resource.Loading
            try {
               val result = repository.register(name, email, phoneNumber, password)
                _register.value = result
                Resource.Success(result)

            }catch (e: Exception){
                _register.value = Resource.Error(e.message.toString())
            }
        }
}