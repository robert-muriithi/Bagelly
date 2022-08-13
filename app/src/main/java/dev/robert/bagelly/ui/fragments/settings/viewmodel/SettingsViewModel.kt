package dev.robert.bagelly.ui.fragments.settings.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.bagelly.data.repository.AuthenticationRepository
import dev.robert.bagelly.data.repository.MainRepository
import dev.robert.bagelly.utils.Resource
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
    @Inject constructor(
        private val repository: AuthenticationRepository
    ): ViewModel() {
        private val _logout = MutableLiveData<Resource<Unit>>()

        suspend fun logout() {
            _logout.value = Resource.Loading
            try {
                repository.logout {
                    _logout.value = Resource.Success(Unit)
                }

            } catch (e: Exception) {
                _logout.value = Resource.Error(e.message ?: "Error Occurred!")
            }
        }
}