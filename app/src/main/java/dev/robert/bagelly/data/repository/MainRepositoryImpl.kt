package dev.robert.bagelly.data.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dev.robert.bagelly.model.Sell
import dev.robert.bagelly.model.Shop
import dev.robert.bagelly.utils.FirestoreCollections
import dev.robert.bagelly.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val storageReference: StorageReference
) :
    MainRepository {
    companion object{
        val firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
    }
    init {
        db.firestoreSettings = firestoreSettings
    }
    private val TAG = "MainRepositoryImpl"
    override suspend fun sell(
        sell: Sell,
        imagesUri: ArrayList<Uri>,
        result: (Resource<List<Sell>>) -> Unit
    ) {
        val ref = storageReference.child("sell/${System.currentTimeMillis()}/${sell.sellerId}")
        withContext(Dispatchers.IO){
            try {
                imagesUri.map {
                    ref.putFile(it).await()
                    val downloadUri = ref.downloadUrl.await()
                    async {
                        sell.images?.add(downloadUri)
                        sell.image1 = downloadUri.toString()
                        sell.image2 = downloadUri.toString()
                        sell.image3 = downloadUri.toString()
                        db.collection(FirestoreCollections.StoreCollection)
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
                            }.await()
                    }
                }.awaitAll()
            } catch (e: Exception) {
                Log.d(TAG, "exception ${e.message}")

            }
            catch (e : Exception){
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun createStore(
        shop: Shop,
        iconImage: Uri,
        result: (Resource<List<Shop>>) -> Unit) {
        val ref = storageReference.child("stores/${System.currentTimeMillis()}")
        withContext(Dispatchers.IO){
            try {
                ref.putFile(iconImage).await()
                val downloadUri = ref.downloadUrl.await()
                shop.shopImage = downloadUri.toString()
                db.collection(FirestoreCollections.StoreCollection)
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
                    }.await()

            } catch (e: Exception) {
                Log.d(TAG, "exception ${e.message}")

            }
            catch (e : Exception){
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun getElectronicStores(result: (Resource<List<Shop>>) -> Unit) {
        withContext(Dispatchers.IO){
            try {
                db.collection(FirestoreCollections.StoreCollection)
                    .whereEqualTo("shopCategory","Electronics and Electrical Shops")
                    .get()
                    .addOnSuccessListener {
                        result.invoke(
                            Resource.Success(it.toObjects(Shop::class.java))
                        )
                    }
                    .addOnFailureListener {
                        result.invoke(
                            Resource.Error(it.message.toString())
                        )
                    }.await()
            } catch (e: Exception) {
                Log.d(TAG, "exception ${e.message}")
            }
            catch (e : Exception){
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun getHomeAndLivingStores(result: (Resource<List<Shop>>) -> Unit) {
        withContext(Dispatchers.IO){
            try {
                db.collection(FirestoreCollections.StoreCollection)
                    .whereEqualTo("shopCategory","Home and Living Stores")
                    .get()
                    .addOnSuccessListener {
                        result.invoke(
                            Resource.Success(it.toObjects(Shop::class.java))
                        )
                    }
                    .addOnFailureListener {
                        result.invoke(
                            Resource.Error(it.message.toString())
                        )
                    }.await()
            } catch (e: Exception) {
                Log.d(TAG, "exception ${e.message}")
            }
            catch (e : Exception){
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun getMobilePhonesStores(result: (Resource<List<Shop>>) -> Unit) {
        withContext(Dispatchers.IO){
            try {
                db.collection(FirestoreCollections.StoreCollection)
                    .whereEqualTo("shopCategory","Mobile Phones Shops")
                    .get()
                    .addOnSuccessListener {
                        result.invoke(
                            Resource.Success(it.toObjects(Shop::class.java))
                        )
                    }
                    .addOnFailureListener {
                        result.invoke(
                            Resource.Error(it.message.toString())
                        )
                    }.await()
            } catch (e: Exception) {
                Log.d(TAG, "exception ${e.message}")
            }
            catch (e : Exception){
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun getFashionShops(result: (Resource<List<Shop>>) -> Unit) {
        withContext(Dispatchers.IO){
            try {
                db.collection(FirestoreCollections.StoreCollection)
                    .whereEqualTo("shopCategory","Fashion shops and Stores")
                    .get()
                    .addOnSuccessListener {
                        result.invoke(
                            Resource.Success(it.toObjects(Shop::class.java))
                        )
                    }
                    .addOnFailureListener {
                        result.invoke(
                            Resource.Error(it.message.toString())
                        )
                    }.await()
            } catch (e: Exception) {
                Log.d(TAG, "exception ${e.message}")
            }
            catch (e : Exception){
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun getGeneralStores(result: (Resource<List<Shop>>) -> Unit) {
        withContext(Dispatchers.IO){
            try {
                db.collection(FirestoreCollections.StoreCollection)
                    .whereEqualTo("shopCategory","General Stores")
                    .get()
                    .addOnSuccessListener {
                        result.invoke(
                            Resource.Success(it.toObjects(Shop::class.java))
                        )
                    }
                    .addOnFailureListener {
                        result.invoke(
                            Resource.Error(it.message.toString())
                        )
                    }.await()
            } catch (e: Exception) {
                Log.d(TAG, "exception ${e.message}")
            }
            catch (e : Exception){
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun getOtherStores(result: (Resource<List<Shop>>) -> Unit) {
        withContext(Dispatchers.IO){
            try {
                db.collection(FirestoreCollections.StoreCollection)
                    .whereEqualTo("shopCategory","Other stores")
                    .get()
                    .addOnSuccessListener {
                        result.invoke(
                            Resource.Success(it.toObjects(Shop::class.java))
                        )
                    }
                    .addOnFailureListener {
                        result.invoke(
                            Resource.Error(it.message.toString())
                        )
                    }.await()
            } catch (e: Exception) {
                Log.d(TAG, "exception ${e.message}")
            }
            catch (e : Exception){
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun getMotorcycleAndVehicleDealers(result: (Resource<List<Shop>>) -> Unit) {
        withContext(Dispatchers.IO){
            try {
                db.collection(FirestoreCollections.StoreCollection)
                    .whereEqualTo("shopCategory","Motorcycle and Vehicle Dealers")
                    .get()
                    .addOnSuccessListener {
                        result.invoke(
                            Resource.Success(it.toObjects(Shop::class.java))
                        )
                    }
                    .addOnFailureListener {
                        result.invoke(
                            Resource.Error(it.message.toString())
                        )
                    }.await()
            } catch (e: Exception) {
                Log.d(TAG, "exception ${e.message}")
            }
            catch (e : Exception){
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun getServiceProvidersShops(result: (Resource<List<Shop>>) -> Unit) {
        withContext(Dispatchers.IO){
            try {
                db.collection(FirestoreCollections.StoreCollection)
                    .whereEqualTo("shopCategory","Service Providers")
                    .get()
                    .addOnSuccessListener {
                        result.invoke(
                            Resource.Success(it.toObjects(Shop::class.java))
                        )
                    }
                    .addOnFailureListener {
                        result.invoke(
                            Resource.Error(it.message.toString())
                        )
                    }.await()
            } catch (e: Exception) {
                Log.d(TAG, "exception ${e.message}")
            }
            catch (e : Exception){
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun getFarmInputStores(result: (Resource<List<Shop>>) -> Unit) {
        withContext(Dispatchers.IO){
            try {
                db.collection(FirestoreCollections.StoreCollection)
                    .whereEqualTo("shopCategory","Farm inputs Stores")
                    .get()
                    .addOnSuccessListener {
                        result.invoke(
                            Resource.Success(it.toObjects(Shop::class.java))
                        )
                    }
                    .addOnFailureListener {
                        result.invoke(
                            Resource.Error(it.message.toString())
                        )
                    }.await()
            } catch (e: Exception) {
                Log.d(TAG, "exception ${e.message}")
            }
            catch (e : Exception){
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }
}
