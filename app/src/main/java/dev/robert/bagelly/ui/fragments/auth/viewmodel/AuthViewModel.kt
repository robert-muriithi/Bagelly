package dev.robert.bagelly.ui.fragments.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.bagelly.data.repository.AuthenticationRepository
import dev.robert.bagelly.utils.Resource
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject constructor(
    private val repository: AuthenticationRepository
    ) : ViewModel() {

        private val _register = MutableLiveData<Resource<AuthResult>>()
        val register : LiveData<Resource<AuthResult>> = _register

        suspend fun register(name: String, email: String, phoneNumber: String, password: String) =
            repository.register(name, email, phoneNumber, password)
                .also { _register.value = it }

}