package dev.robert.bagelly.ui.fragments.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.auth.User
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.bagelly.data.repository.MainRepository
import dev.robert.bagelly.model.Users
import dev.robert.bagelly.utils.Resource
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject constructor(
        private val repository: MainRepository
    )
    : ViewModel(){
        private val _user = MutableLiveData<Resource<List<Users>>>()
        val user = _user as LiveData<Resource<List<Users>>>

        suspend fun getUser(){
            _user.value = Resource.Loading
            try {
                repository.getUsers {
                    _user.value = it
                }
            }catch (e : Exception){
                _user.value = Resource.Error(e.message.toString())
            }
        }
}