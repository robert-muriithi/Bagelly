package dev.robert.bagelly.ui.fragments.account.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.bagelly.data.repository.MainRepository
import dev.robert.bagelly.model.Users
import dev.robert.bagelly.utils.Resource
import kotlinx.coroutines.launch
import java.util.concurrent.Flow
import javax.inject.Inject
@HiltViewModel
class MyAccountViewModel
    @Inject constructor(
        private val repository: MainRepository
    ): ViewModel() {
    private  val TAG = "MyAccountViewModel"

    private val _user = MutableLiveData<Resource<Users>>()
    val user = _user as LiveData<Resource<Users>>
    private val _updateUser = MutableLiveData<Resource<Users>>()
    val updateUser = _updateUser as LiveData<Resource<Users>>

    suspend fun getSingleUser(userId: String) {
        _user.value = Resource.Loading
        try {
             repository.getSingleUser(userId){
                _user.value = it
            }
            Log.d(TAG, "getSingleUser: ${_user.value}")

        } catch (e: Exception) {
            _user.value = (Resource.Error(e.message ?: "Something went wrong"))
        }
    }

    suspend fun updateUser(userId: String, userProfile: Uri, user: Users) {
        _updateUser.value = Resource.Loading
        try {
            repository.updateUser(userId, userProfile, user){
                _updateUser.value = it
            }
        } catch (e: Exception) {
            _updateUser.value = (Resource.Error(e.message ?: "Something went wrong"))
        }
    }

}