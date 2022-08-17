package dev.robert.bagelly.ui.fragments.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.auth.User
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.bagelly.data.repository.MainRepository
import dev.robert.bagelly.model.Sell
import dev.robert.bagelly.model.Users
import dev.robert.bagelly.utils.Resource
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject constructor(
        private val repository: MainRepository
    )
    : ViewModel(){

        private val _sell = MutableLiveData<Resource<List<Sell>>>()
        val sell = _sell as LiveData<Resource<List<Sell>>>

        private val _recentSell = MutableLiveData<Resource<List<Sell>>>()
        val recentSell = _recentSell as LiveData<Resource<List<Sell>>>

        suspend fun getRecentSells(){
            _recentSell.value = Resource.Loading
             try {
                 repository.getRecentSells {
                     _recentSell.value = it
                 }
             }catch (e: Exception){
                 _recentSell.value = Resource.Error(e.message.toString())
             }catch (e : FirebaseException){
                 _recentSell.value = Resource.Error(e.message.toString())
             }

        }


        suspend fun getSells(){
            _sell.value = Resource.Loading
            try {
                repository.getSells {
                    _sell.value = it
                }
            }catch (e : Exception){
                _sell.value = Resource.Error(e.message.toString())
            }
        }

}