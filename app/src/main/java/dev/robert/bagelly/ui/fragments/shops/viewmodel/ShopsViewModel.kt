package dev.robert.bagelly.ui.fragments.shops.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.bagelly.data.repository.MainRepository
import dev.robert.bagelly.model.Post
import dev.robert.bagelly.model.Shop
import dev.robert.bagelly.utils.Resource
import javax.inject.Inject

@HiltViewModel
class ShopsViewModel
    @Inject constructor(
        private val repository: MainRepository
    ): ViewModel(){

        private val _createShop = MutableLiveData<Resource<List<Shop>>>()
        val createShop: LiveData<Resource<List<Shop>>>
            get() = _createShop
        private val _getElectronicShops = MutableLiveData<Resource<List<Shop>>>()
        val getElectronicShops: LiveData<Resource<List<Shop>>>
            get() = _getElectronicShops
        private val _getHomeAndLivingStores = MutableLiveData<Resource<List<Shop>>>()
        val getHomeAndLivingStores: LiveData<Resource<List<Shop>>>
            get() = _getHomeAndLivingStores
        private val _getMobilePhoneShops = MutableLiveData<Resource<List<Shop>>>()
        val getMobilePhoneShops: LiveData<Resource<List<Shop>>>
            get() = _getMobilePhoneShops
        private val _getFashionShops = MutableLiveData<Resource<List<Shop>>>()
        val getFashionShops: LiveData<Resource<List<Shop>>>
            get() = _getFashionShops
        private val _getMotorsDealers = MutableLiveData<Resource<List<Shop>>>()
        val getMotorsDealers: LiveData<Resource<List<Shop>>>
            get() = _getMotorsDealers
        private val _getGeneralStores = MutableLiveData<Resource<List<Shop>>>()
        val getGeneralStores: LiveData<Resource<List<Shop>>>
            get() = _getGeneralStores
        private val _getServiceProviders = MutableLiveData<Resource<List<Shop>>>()
        val getServiceProviders: LiveData<Resource<List<Shop>>>
            get() = _getServiceProviders
       private val _getFarmInputsStores = MutableLiveData<Resource<List<Shop>>>()
        val getFarmInputsStores: LiveData<Resource<List<Shop>>>
            get() = _getFarmInputsStores
       private val _getOtherStores = MutableLiveData<Resource<List<Shop>>>()
        val getOtherStores: LiveData<Resource<List<Shop>>>
            get() = _getOtherStores
        private val _getPosts = MutableLiveData<Resource<List<Post>>>()
        val getPosts: LiveData<Resource<List<Post>>>
            get() = _getPosts
        private val _postAd = MutableLiveData<Resource<List<Post>>>()
        val postAd: LiveData<Resource<List<Post>>>
            get() = _postAd

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

        suspend fun getElectronicStores(){
            _getElectronicShops.value = Resource.Loading
            try{
                repository.getElectronicStores{
                    _getElectronicShops.value = it
                }
            }
            catch (e: Exception){
                _getElectronicShops.value = Resource.Error(e.message.toString())
            }
        }
        suspend fun getHomeAndLivingStores(){
            _getHomeAndLivingStores.value = Resource.Loading
            try{
                repository.getHomeAndLivingStores{
                    _getHomeAndLivingStores.value = it
                }
            }
            catch (e: Exception){
                _getHomeAndLivingStores.value = Resource.Error(e.message.toString())
            }
        }
        suspend fun getMobilePhonesStores(){
            _getMobilePhoneShops.value = Resource.Loading
            try{
                repository.getMobilePhonesStores{
                    _getMobilePhoneShops.value = it
                }
            }
            catch (e: Exception){
                _getMobilePhoneShops.value = Resource.Error(e.message.toString())
            }
        }
        suspend fun getFashionStores(){
            _getFashionShops.value = Resource.Loading
            try{
                repository.getFashionShops{
                    _getFashionShops.value = it
                }
            }
            catch (e: Exception){
                _getFashionShops.value = Resource.Error(e.message.toString())
            }
        }
        suspend fun getMotorsDealers(){
            _getMotorsDealers.value = Resource.Loading
            try{
                repository.getMotorcycleAndVehicleDealers{
                    _getMotorsDealers.value = it
                }
            }
            catch (e: Exception){
                _getMotorsDealers.value = Resource.Error(e.message.toString())
            }
        }
        suspend fun getGeneralStores(){
            _getGeneralStores.value = Resource.Loading
            try{
                repository.getGeneralStores{
                    _getGeneralStores.value = it
                }
            }
            catch (e: Exception){
                _getGeneralStores.value = Resource.Error(e.message.toString())
            }
        }
        suspend fun getServiceProviders(){
            _getServiceProviders.value = Resource.Loading
            try{
                repository.getServiceProvidersShops{
                    _getServiceProviders.value = it
                }
            }
            catch (e: Exception){
                _getServiceProviders.value = Resource.Error(e.message.toString())
            }
        }
        suspend fun getFarmInputsStores(){
            _getFarmInputsStores.value = Resource.Loading
            try{
                repository.getFarmInputStores{
                    _getFarmInputsStores.value = it
                }
            }
            catch (e: Exception){
                _getFarmInputsStores.value = Resource.Error(e.message.toString())
            }
        }
        suspend fun getOtherStore(){
            _getOtherStores.value = Resource.Loading
            try{
                repository.getOtherStores{
                    _getOtherStores.value = it
                }
            }
            catch (e: Exception){
                _getOtherStores.value = Resource.Error(e.message.toString())
            }
        }
        suspend fun postAd(post: Post, image: Uri){
            _postAd.value = Resource.Loading
            try{
                repository.postAd(post, image){
                    _postAd.value = it
                }
            }
            catch (e: Exception){
                _postAd.value = Resource.Error(e.message.toString())
            }
        }

        suspend fun getPosts(){
            _getPosts.value = Resource.Loading
            try{
                repository.getPosts{
                    _getPosts.value = it
                }
            }
            catch (e: Exception){
                _getPosts.value = Resource.Error(e.message.toString())
            }
        }

}