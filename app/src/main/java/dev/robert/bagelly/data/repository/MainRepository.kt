package dev.robert.bagelly.data.repository

import android.net.Uri
import dev.robert.bagelly.model.Sell
import dev.robert.bagelly.model.Shop
import dev.robert.bagelly.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun sell(sell: Sell, result: (Resource<List<Sell>>) -> Unit)
    suspend fun addMultipleImages(imagesUri: List<Uri>, result: (Resource<List<Uri>>) -> Unit)
    suspend fun createStore(shop: Shop, iconImage : Uri, result: (Resource<List<Shop>>) -> Unit)
}