package dev.robert.bagelly.ui.fragments.sell.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.bagelly.data.repository.MainRepository
import dev.robert.bagelly.model.Sell
import dev.robert.bagelly.ui.fragments.sell.SellFragment2Args
import dev.robert.bagelly.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellViewModel
   @Inject constructor(
    private val repository: MainRepository)
    : ViewModel() {

    private val _sell = MutableLiveData<Resource<List<Sell>>>()
    val sell: LiveData<Resource<List<Sell>>> = _sell
    private val _deleteSell = MutableLiveData<Resource<Sell>>()
    val deleteSell: LiveData<Resource<Sell>> = _deleteSell

    private val _getAllSells = MutableLiveData<Resource<List<Sell>>>()
    val getAllSells = _getAllSells as LiveData<Resource<List<Sell>>>

    fun getAllSells() {
        viewModelScope.launch {
            _sell.value = Resource.Loading
            try {
                repository.getAllSells {
                    _getAllSells.value = it
                }
            } catch (e: Exception) {
                _sell.value = Resource.Error(e.message ?: "Error Occurred!")
            }
        }
    }

    fun deleteItem(sell: Sell){
        viewModelScope.launch {
            _deleteSell.value = Resource.Loading
            try {
                repository.deleteSellItem(sell) {
                    _deleteSell.value = it
                }
            } catch (e: Exception) {
                _deleteSell.value = Resource.Error(e.message ?: "Error Occurred!")
            }
        }
    }

    fun sell(sell: Sell, imageList : ArrayList<String>) {
        viewModelScope.launch {
            _sell.value = Resource.Loading
            try {
                repository.sell(sell, imageList) {
                    _sell.value = it
                }
            } catch (e: Exception) {
                _sell.value = Resource.Error(e.message ?: "Error Occurred!")
            }
        }
    }
}