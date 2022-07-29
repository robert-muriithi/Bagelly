package dev.robert.bagelly.data.repository

import android.net.Uri
import dev.robert.bagelly.model.Sell
import dev.robert.bagelly.model.Shop
import dev.robert.bagelly.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun sell(sell: Sell, result: (Resource<List<Sell>>) -> Unit)
    suspend fun createShop(shop: Shop, result: (Resource<List<Shop>>) -> Unit)
    suspend fun addImageToFirebaseStorage(imageUri: Uri , result: (Flow<Resource<Uri>>) -> Unit)
    suspend fun addImageToFirestore(downloadUrl: Uri, result:  (Flow<Resource<Boolean>>) -> Unit)
    suspend fun getImageFromFirestore(result : (Flow<Resource<String>>) -> Unit)
}