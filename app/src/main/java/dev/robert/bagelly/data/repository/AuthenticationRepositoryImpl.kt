package dev.robert.bagelly.data.repository

import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dev.robert.bagelly.model.Users
import dev.robert.bagelly.utils.Resource
import javax.inject.Inject

class AuthenticationRepositoryImpl
@Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
    ) : AuthenticationRepository {
    override suspend fun saveUser(user: Users, result: (Resource<List<Users>>) -> Unit) {
        auth.createUserWithEmailAndPassword(user.email!!, user.password!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = auth.currentUser
                    currentUser?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val user = Users(user.id, user.name, user.email,user.phoneNumber)
                                db.collection("users").document(user.id!!).set(user)
                                /*result(Resource.Success(listOf(user)))*/
                                result.invoke(
                                  Resource.Success(listOf(user))
                                )

                            } else {
                                result.invoke(
                                    Resource.Error(task.exception?.message!!)
                                )
                            }
                        }
                    result.invoke(
                        Resource.Success(listOf(user))
                    )
                } else {
                    result.invoke(
                        Resource.Error(task.exception?.message!!)
                    )
                }
            }
    }
}