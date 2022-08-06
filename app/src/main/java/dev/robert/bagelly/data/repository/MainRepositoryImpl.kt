package dev.robert.bagelly.data.repository

import android.net.Uri
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dev.robert.bagelly.model.Sell
import dev.robert.bagelly.model.Shop
import dev.robert.bagelly.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val storageReference: StorageReference,
    private val storage: FirebaseStorage
) :
    MainRepository {


    override suspend fun sell(sell: Sell, result: (Resource<List<Sell>>) -> Unit) {
        db.collection("Sell_Items")
            .add(sell)
            .addOnSuccessListener {
                result.invoke(
                    Resource.Success(arrayListOf(sell))
                )
            }
            .addOnFailureListener {
                result.invoke(
                    Resource.Error(it.message.toString())
                )
            }
    }

    override suspend fun addMultipleImages(
        imagesUri: List<Uri>,
        result: (Resource<List<Uri>>) -> Unit
    ) {
        try {
            val uris = withContext(Dispatchers.IO) {
                imagesUri.map { uri ->
                    async {
                        storageReference.child(uri.lastPathSegment!!)
                            .putFile(uri)
                            .await()
                            .storage.downloadUrl.await()
                    }
                }.awaitAll()
            }
            result.invoke(
                Resource.Success(uris)
            )
        } catch (e: Exception) {
            result.invoke(
                Resource.Error(e.message.toString())
            )
        } catch (e: FirebaseException) {
            result.invoke(
                Resource.Error(e.message.toString())
            )
        }
    }

    override suspend fun createStore(
        shop: Shop,
        iconImage: Uri,
        result: (Resource<List<Shop>>) -> Unit
    ) {
         val uris =withContext(Dispatchers.IO) {
            async {
                storageReference.child("stores/${System.currentTimeMillis()}")
                    .putFile(iconImage)
                    .addOnSuccessListener {
                        db.collection("stores")
                            .add(shop)
                            .addOnSuccessListener {
                                result.invoke(
                                    Resource.Success(arrayListOf(shop))
                                )
                            }
                            .addOnFailureListener {
                                result.invoke(
                                    Resource.Error(it.message.toString())
                                )
                            }
                    }
                    .await()
                    .storage
                    .downloadUrl
                    .await()
            }
        }
        shop.shopImage = uris.toString()
    }
}
