package dev.robert.bagelly.ui.fragments.shops.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.bagelly.data.repository.MainRepository
import dev.robert.bagelly.model.Shop
import dev.robert.bagelly.utils.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class ShopsViewModel
    @Inject constructor(
        private val repository: MainRepository
    ): ViewModel(){

        private val _createShop = MutableLiveData<Resource<List<Shop>>>()
        val createShop: LiveData<Resource<List<Shop>>>
            get() = _createShop

        suspend fun createStore(shop: Shop, uri: Uri){
            _createShop.value = Resource.Loading
            try{
                repository.createStore(shop, uri){
                    _createShop.value = it
                }
            }
            catch (e: Exception){
                _createShop.value = Resource.Error(e.message.toString())
            }
        }
}