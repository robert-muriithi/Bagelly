package dev.robert.bagelly.data.repository

import android.net.Uri
import dev.robert.bagelly.model.Sell
import dev.robert.bagelly.model.Shop
import dev.robert.bagelly.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun sell(sell: Sell, imagesUri: ArrayList<Uri>, result: (Resource<List<Sell>>) -> Unit)
    suspend fun createStore(shop: Shop, iconImage : Uri, result: (Resource<List<Shop>>) -> Unit)
    suspend fun getElectronicStores(result: (Resource<List<Shop>>) -> Unit)
    suspend fun getHomeAndLivingStores(result: (Resource<List<Shop>>) -> Unit)
    suspend fun getMobilePhonesStores(result: (Resource<List<Shop>>) -> Unit)
    suspend fun getFashionShops(result: (Resource<List<Shop>>) -> Unit)
    suspend fun getGeneralStores(result: (Resource<List<Shop>>) -> Unit)
    suspend fun getOtherStores(result: (Resource<List<Shop>>) -> Unit)
    suspend fun getMotorcycleAndVehicleDealers(result: (Resource<List<Shop>>) -> Unit)
    suspend fun getServiceProvidersShops(result: (Resource<List<Shop>>) -> Unit)
    suspend fun getFarmInputStores(result: (Resource<List<Shop>>) -> Unit)
}