package dev.robert.bagelly.data.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dev.robert.bagelly.model.Sell
import dev.robert.bagelly.model.Shop
import dev.robert.bagelly.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val storageReference: StorageReference,
    private val storage: FirebaseStorage
) :
    MainRepository {

    private val TAG = "MainRepositoryImpl"
    override suspend fun sell(
        sell: Sell,
        imagesUri: List<Uri>,
        result: (Resource<List<Sell>>) -> Unit
    ) {
        val ref = storageReference.child("sell/${System.currentTimeMillis()}")
        //loop imageList
        imagesUri.forEach { it ->
            val uploadTask = ref.child(it.lastPathSegment!!).putFile(it)
            uploadTask.addOnSuccessListener {
                Log.d(TAG, "Upload Success")
            }
                .addOnFailureListener {
                    Log.d(TAG, "exception ${it.message}")
                }
            val task = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                ref.child(it.lastPathSegment!!).downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    sell.images?.add(downloadUri)
                    db.collection("sell")
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
                } else {
                    Log.d(TAG, "exception ${task.exception?.message}")
                }
            }
            task.await()
        }

    }

    /*override suspend fun addMultipleImages(
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
    }*/

    override suspend fun createStore(
        shop: Shop,
        iconImage: Uri,
        result: (Resource<List<Shop>>) -> Unit
    ) {
        /*CoroutineScope(Dispatchers.IO).launch {
            storageReference.child("stores/${System.currentTimeMillis()}")
                .putFile(iconImage)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        if (it.isComplete){
                            val iconImageUrl = it.result?.storage?.downloadUrl?.toString()
                            shop.shopImage = iconImageUrl
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
                    } else {
                        result.invoke(
                            Resource.Error(it.exception?.message.toString())
                        )
                    }
                }.await()
        }*/
        val ref = storageReference.child("stores/${System.currentTimeMillis()}")
        val uploadTask = ref.putFile(iconImage)
        uploadTask.addOnSuccessListener {
            Log.d(TAG, "image Uploaded Successfully: ")
        }.addOnFailureListener {
            Log.d(TAG, "Exception: ${it.message}")
        }

        val task = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                shop.shopImage = downloadUri.toString()
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
            } else {
                result.invoke(
                    Resource.Error(task.exception?.message.toString())
                )
            }
        }
        task.await()
    }
}
