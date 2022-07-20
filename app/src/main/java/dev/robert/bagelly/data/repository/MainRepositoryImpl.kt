package dev.robert.bagelly.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import dev.robert.bagelly.model.Sell
import dev.robert.bagelly.utils.Resource
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) :
    MainRepository {

    override suspend fun sell(item: Sell, result: (Resource<List<Sell>>) -> Unit) {
        db.collection("sell_items")
            .get()
            .addOnSuccessListener { snapshot ->
                val list = ArrayList<Sell>()
                for (data in snapshot.documents) {
                    val sell = data.toObject(Sell::class.java)
                    if (sell != null) {
                        list.add(sell)
                    }
                }
                result.invoke(
                    Resource.Success(list)
                )
            }
            .addOnFailureListener {
                result.invoke(
                    Resource.Error(it.message.toString())
                )
            }
    }
}