package dev.robert.bagelly.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import dev.robert.bagelly.model.Sell
import dev.robert.bagelly.utils.Resource
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) :
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
}