package dev.robert.bagelly.ui.fragments.favorites.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.bagelly.data.repository.MainRepository
import dev.robert.bagelly.model.Sell
import dev.robert.bagelly.utils.Resource
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    private val _favoriteItem = MutableLiveData<Resource<List<Sell>>>()
    val favoriteItem = _favoriteItem as LiveData<Resource<List<Sell>>>
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading as LiveData<Boolean>

    private val _deleteAllItems = MutableLiveData<Resource<List<Sell>>>()
    val deleteAllItems = _deleteAllItems as LiveData<Resource<List<Sell>>>

    suspend fun deleteAllItems() {
        _deleteAllItems.value = Resource.Loading
        try{
            repository.deleteAllFavouriteItems { _deleteAllItems.value = it }
        }catch (e: Exception){
            _deleteAllItems.value = Resource.Error(e.message.toString())
        }
    }

    fun isLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    suspend fun getFavoriteItem() {
        _favoriteItem.value = Resource.Loading
        try {
            repository.getFavouriteItems {
                _favoriteItem.value = it
            }
        } catch (e: Exception) {
            _favoriteItem.value = Resource.Error(e.message.toString())
        }
    }
}