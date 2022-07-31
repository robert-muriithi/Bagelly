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

        private val _shops = MutableLiveData<Resource<List<Shop>>>()
        val shops: LiveData<Resource<List<Shop>>>
            get() = _shops

      suspend fun createShop(shop: Shop){
          _shops.value = Resource.Loading
          try{
                repository.createShop(shop){
                    _shops.value = it
                }
          }
          catch (e: Exception){
              _shops.value = Resource.Error(e.message.toString())
          }
      }
        private val _uploadImage = MutableLiveData<Resource<String>>()
        val uploadImage: LiveData<Resource<String>>
            get() = _uploadImage

        /*suspend fun uploadImage(image: Uri) = flow {
            _uploadImage.value = Resource.Loading
            try{
                val imageUrl = repository.addImageToFirebaseStorage(image){
                    emit(it)
                }
                 emit(Resource.Success(imageUrl))
            }
            catch (e: Exception){
                _uploadImage.value = Resource.Error(e.message.toString())
            }
        }*/

}