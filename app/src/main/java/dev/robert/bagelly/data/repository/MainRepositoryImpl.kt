package dev.robert.bagelly.data.repository

import android.net.Uri
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import dev.robert.bagelly.model.Sell
import dev.robert.bagelly.model.Shop
import dev.robert.bagelly.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val storageReference: StorageReference,
    private val collectionReference: CollectionReference
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

    override suspend fun createShop(shop: Shop, result: (Resource<List<Shop>>) -> Unit) {
        db.collection("shops")
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

    override suspend fun addImageToFirebaseStorage(
        imageUri: Uri,
        result: (Flow<Resource<Uri>>) -> Unit
    ) {
        val imageRef = storageReference.child("images/${imageUri.lastPathSegment}")
        imageRef.putFile(imageUri)
            .addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener {
                    result.invoke(
                        flow {
                            emit(Resource.Success(it))
                        }
                    )
                }
            }
            .addOnFailureListener {
                result.invoke(
                    flow {
                        emit(Resource.Error(it.message.toString()))
                    }
                )
            }
    }

    override suspend fun addImageToFirestore(
        downloadUrl: Uri,
        result: (Flow<Resource<Boolean>>) -> Unit
    ) {
        db.collection("images")
            .add(downloadUrl)
            .addOnSuccessListener {
                result.invoke(
                    flow {
                        emit(Resource.Success(true))
                    }
                )
            }
            .addOnFailureListener {
                result.invoke(
                    flow {
                        emit(Resource.Error(it.message.toString()))
                    }
                )
            }
    }

    override suspend fun getImageFromFirestore(result: (Flow<Resource<String>>) -> Unit) {
        db.collection("images")
            .get()
            .addOnSuccessListener {
                result.invoke(
                    flow {
                        it.documents.forEach {
                            emit(Resource.Success(it.id))
                        }
                    }
                )
            }
            .addOnFailureListener {
                result.invoke(
                    flow {
                        emit(Resource.Error(it.message.toString()))
                    }
                )
            }
    }
}