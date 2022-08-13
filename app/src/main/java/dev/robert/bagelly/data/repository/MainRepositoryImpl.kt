package dev.robert.bagelly.data.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dev.robert.bagelly.model.Post
import dev.robert.bagelly.model.Sell
import dev.robert.bagelly.model.Shop
import dev.robert.bagelly.model.Users
import dev.robert.bagelly.utils.FirestoreCollections
import dev.robert.bagelly.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import android.widget.Toast

import android.content.Intent
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class MainRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val storageReference: StorageReference
) :
    MainRepository {
    companion object {
        val firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
    }

    init {
        db.firestoreSettings = firestoreSettings
    }

    private val TAG = "MainRepositoryImpl"


    override suspend fun getSingleUser(
        userId: String,
        result: (Resource<Users>) -> Unit
    ): Resource<Users> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val user = db.collection(FirestoreCollections.UserCollection).document(userId).get().await()
                withContext(Dispatchers.Main) {
                    Log.d(TAG, "getSingleUser: ${user.data}")
                    result(Resource.Success(user.toObject(Users::class.java)!!))
                }
                Resource.Success(user.toObject(Users::class.java)!!)
            } catch (e: Exception) {
                Resource.Error(e.message!!)
            } catch (e: FirebaseException) {
                Resource.Error(e.message!!)
            }
        }
    }

    override suspend fun updateUser(
        userId: String,
        userProfile: Uri,
        user: Users,
        result: (Resource<Users>) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            try {
                val id = db.collection(FirestoreCollections.UserCollection).document().id
                Log.d(TAG, "currentId: $id")

                val storageReference =
                    FirebaseStorage.getInstance().reference.child("users/$id")
                val uploadTask = storageReference.putFile(userProfile)
                uploadTask.addOnSuccessListener {
                    storageReference.downloadUrl.addOnSuccessListener {
                        user.profileImageUrl = it.toString()
                        db.collection(FirestoreCollections.UserCollection).document(userId)
                            .set(user, SetOptions.merge())
                            .addOnSuccessListener {
                                result.invoke(Resource.Success(user))
                            }
                            .addOnFailureListener {
                                result.invoke(Resource.Error(it.message.toString()))
                            }
                    }
                }
                    .addOnFailureListener {
                        result.invoke(Resource.Error(it.message.toString()))
                    }.await()
            } catch (e: Exception) {
                result(Resource.Error(e.message!!))
            } catch (e: FirebaseException) {
                result(Resource.Error(e.message!!))
            }
        }
    }

    override suspend fun sell(
        sell: Sell,
        imagesUri: ArrayList<Uri>,
        result: (Resource<List<Sell>>) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            try {
                val sellId = db.collection(FirestoreCollections.SellCollection).document().id
                val storageReference = FirebaseStorage.getInstance().reference.child("sell/$sellId")
                var i = 0
                while (i < imagesUri.size) {
                    val uploadTask = storageReference.putFile(imagesUri[i])
                    uploadTask.addOnSuccessListener {
                        val task = storageReference.downloadUrl
                        task.addOnSuccessListener {
                            sell.images?.add(it)
                            when (i) {
                                0 -> {
                                    sell.image1 = it.toString()
                                }
                                1 -> {
                                    sell.image2 = it.toString()
                                }
                                2 -> {
                                    sell.image3 = it.toString()
                                }
                            }
                            i++
                            if (i == imagesUri.size) {
                                db.collection(FirestoreCollections.SellCollection)
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
                        }

                    }
                }

            } catch (e: Exception) {
                Log.d(TAG, "exception ${e.message}")

            } catch (e: Exception) {
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun createStore(
        shop: Shop,
        iconImage: Uri,
        result: (Resource<List<Shop>>) -> Unit
    ) {
        val ref = storageReference.child("stores/${System.currentTimeMillis()}")
        withContext(Dispatchers.IO) {
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
                    }
            } catch (e: Exception) {
                Log.d(TAG, "exception ${e.message}")

            } catch (e: Exception) {
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun getElectronicStores(result: (Resource<List<Shop>>) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                db.collection(FirestoreCollections.StoreCollection)
                    .whereEqualTo("shopCategory", "Electronics and Electrical Shops")
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
            } catch (e: Exception) {
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun getHomeAndLivingStores(result: (Resource<List<Shop>>) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                db.collection(FirestoreCollections.StoreCollection)
                    .whereEqualTo("shopCategory", "Home and Living Stores")
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
            } catch (e: Exception) {
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun getMobilePhonesStores(result: (Resource<List<Shop>>) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                db.collection(FirestoreCollections.StoreCollection)
                    .whereEqualTo("shopCategory", "Mobile Phones Shops")
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
            } catch (e: Exception) {
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun getFashionShops(result: (Resource<List<Shop>>) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                db.collection(FirestoreCollections.StoreCollection)
                    .whereEqualTo("shopCategory", "Fashion shops and Stores")
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
            } catch (e: Exception) {
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun getGeneralStores(result: (Resource<List<Shop>>) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                db.collection(FirestoreCollections.StoreCollection)
                    .whereEqualTo("shopCategory", "General Stores")
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
            } catch (e: Exception) {
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun getOtherStores(result: (Resource<List<Shop>>) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                db.collection(FirestoreCollections.StoreCollection)
                    .whereEqualTo("shopCategory", "Other stores")
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
            } catch (e: Exception) {
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun getMotorcycleAndVehicleDealers(result: (Resource<List<Shop>>) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                db.collection(FirestoreCollections.StoreCollection)
                    .whereEqualTo("shopCategory", "Motorcycle and Vehicle Dealers")
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
            } catch (e: Exception) {
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun getServiceProvidersShops(result: (Resource<List<Shop>>) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                db.collection(FirestoreCollections.StoreCollection)
                    .whereEqualTo("shopCategory", "Service Providers")
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
            } catch (e: Exception) {
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun getFarmInputStores(result: (Resource<List<Shop>>) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                db.collection(FirestoreCollections.StoreCollection)
                    .whereEqualTo("shopCategory", "Farm inputs Stores")
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
            } catch (e: Exception) {
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun postAd(
        post: Post,
        postImage: Uri,
        result: (Resource<List<Post>>) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            try {
                val postId = db.collection(FirestoreCollections.PostCollection).document().id
                val storageReference =
                    FirebaseStorage.getInstance().reference.child("post_images/$postId")
                val uploadTask = storageReference.putFile(postImage)
                uploadTask.addOnSuccessListener {
                    storageReference.downloadUrl.addOnSuccessListener {
                        post.postImage = it.toString()
                        db.collection(FirestoreCollections.PostCollection).document(postId)
                            .set(post)
                            .addOnSuccessListener {
                                result.invoke(Resource.Success(listOf(post)))
                            }
                            .addOnFailureListener {
                                result.invoke(Resource.Error(it.message.toString()))
                            }
                    }
                }
                    .addOnFailureListener {
                        result.invoke(Resource.Error(it.message.toString()))
                    }
            } catch (e: Exception) {
                Log.d(TAG, "exception ${e.message}")
            } catch (e: Exception) {
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun getPosts(result: (Resource<List<Post>>) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                val shopId = db.collection(FirestoreCollections.PostCollection).document().id
                db.collection(FirestoreCollections.PostCollection)
                    .get()
                    .addOnSuccessListener {
                        result.invoke(
                            Resource.Success(it.toObjects(Post::class.java))
                        )
                    }
                    .addOnFailureListener {
                        result.invoke(
                            Resource.Error(it.message.toString())
                        )
                    }.await()
            } catch (e: Exception) {
                Log.d(TAG, "exception ${e.message}")
            } catch (e: Exception) {
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun deleteSinglePost(post: Post, result: (Resource<Post>) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                val postId = db.collection(FirestoreCollections.PostCollection).document().id
                db.collection(FirestoreCollections.PostCollection).document(postId).delete()
                    .addOnSuccessListener {
                        result.invoke(Resource.Success(post))
                    }
                    .addOnFailureListener {
                        result.invoke(Resource.Error(it.message.toString()))
                    }.await()
            } catch (e: Exception) {
                Log.d(TAG, "exception ${e.message}")
            } catch (e: Exception) {
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun getSells(result: (Resource<List<Sell>>) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                db.collection(FirestoreCollections.SellCollection)
                    .get()
                    .addOnSuccessListener {
                        result.invoke(
                            Resource.Success(it.toObjects(Sell::class.java))
                        )
                    }
                    .addOnFailureListener {
                        result.invoke(
                            Resource.Error(it.message.toString())
                        )
                    }.await()
            } catch (e: Exception) {
                Log.d(TAG, "exception ${e.message}")
            } catch (e: Exception) {
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun addToFavourite(sell: Sell, result: (Resource<Sell>) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                val sellId = db.collection(FirestoreCollections.SellCollection).document().id
                db.collection(FirestoreCollections.FavoriteCollection).document(sellId).set(sell)
                    .addOnSuccessListener {
                        result.invoke(Resource.Success(sell))
                    }
                    .addOnFailureListener {
                        result.invoke(Resource.Error(it.message.toString()))
                    }.await()
            } catch (e: Exception) {
                Log.d(TAG, "exception ${e.message}")
            } catch (e: Exception) {
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun getFavouriteItems(result: (Resource<List<Sell>>) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                db.collection(FirestoreCollections.FavoriteCollection)
                    .get()
                    .addOnSuccessListener {
                        result.invoke(
                            Resource.Success(it.toObjects(Sell::class.java))
                        )
                    }
                    .addOnFailureListener {
                        result.invoke(
                            Resource.Error(it.message.toString())
                        )
                    }.await()
            } catch (e: Exception) {
                Log.d(TAG, "exception ${e.message}")
            } catch (e: Exception) {
                result.invoke(Resource.Error(e.message.toString()))
            }
        }
    }
}

