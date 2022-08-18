package dev.robert.bagelly.ui.fragments.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.auth.User
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.bagelly.data.repository.MainRepository
import dev.robert.bagelly.model.Sell
import dev.robert.bagelly.model.Shop
import dev.robert.bagelly.model.Users
import dev.robert.bagelly.utils.Resource
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject constructor(
        private val repository: MainRepository
    )
    : ViewModel(){

        private val _exclusiveShops = MutableLiveData<Resource<List<Shop>>>()
        val exclusiveShops = _exclusiveShops as LiveData<Resource<List<Shop>>>

        private val _recentSell = MutableLiveData<Resource<List<Sell>>>()
        val recentSell = _recentSell as LiveData<Resource<List<Sell>>>

        private val _isLoading = MutableLiveData<Boolean>()
        val isLoading = _isLoading as LiveData<Boolean>

        private val _recommendedSells = MutableLiveData<Resource<List<Sell>>>()
        val recommendedSells = _recommendedSells as LiveData<Resource<List<Sell>>>

        fun isLoading(isLoading: Boolean){
            _isLoading.value = isLoading
        }


        suspend fun getRecommendedSells(){
            _recommendedSells.value = Resource.Loading
            try {
                repository.getRecommendedSells(){
                    _recommendedSells.value = it
                }
            }catch (e : FirebaseException){
                _recommendedSells.value = Resource.Error(e.message.toString())
            }catch (e : Exception){
                _recommendedSells.value = Resource.Error(e.message.toString())
            }
        }


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

        suspend fun getExclusiveShops(){
            _exclusiveShops.value = Resource.Loading
            try {
                repository.getExclusiveStores {
                    _exclusiveShops.value = it
                }
            }catch (e: Exception){
                _exclusiveShops.value = Resource.Error(e.message.toString())
            }catch (e : FirebaseException){
                _exclusiveShops.value = Resource.Error(e.message.toString())
            }
        }


}