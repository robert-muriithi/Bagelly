package dev.robert.bagelly.data.repository

import android.app.Application
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.robert.bagelly.model.Users
import dev.robert.bagelly.utils.FirestoreCollections
import dev.robert.bagelly.utils.Resource
import javax.inject.Inject

class AuthenticationRepositoryImpl
@Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    @ApplicationContext private val application: Application
) : AuthenticationRepository {
    /*override suspend fun register(
        name: String,
        email: String,
        phoneNothing: String,
        password: String
    ): Resource<AuthResult> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password)
            result.addOnCompleteListener {
                if (it.isSuccessful){
                    val user = auth.currentUser
                    user?.sendEmailVerification()
                    val uid = user?.uid
                    val userData = Users(uid, name, email, phoneNothing)
                    db.collection("users").document(uid!!).set(userData)
                }
                else{
                    Toast.makeText(application, it.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
            Resource.Success(result.result)
        } catch (e: Exception) {
            Toast.makeText(application, "${e.message}", Toast.LENGTH_SHORT).show()
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun login(email: String, password: String): Resource<AuthResult> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password)
            Resource.Success(result.result)
        } catch (e: Exception) {
            Toast.makeText(application, "${e.message}", Toast.LENGTH_SHORT).show()
            Resource.Error(e.message!!)
        }
    }

    override suspend fun forgotPassword(email: String): Resource<Any> {
        return try {
            auth.sendPasswordResetEmail(email)
            Resource.Success(Any())
        } catch (e: Exception) {
            Toast.makeText(application, "${e.message}", Toast.LENGTH_SHORT).show()
            Resource.Error(e.message!!)
        }
    }*/
    override suspend fun registerUser(
        email: String,
        password: String,
        users: Users,
        result: (Resource<String>) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val user = auth.currentUser
                    user?.sendEmailVerification()
                    val uid = user?.uid
                    users.id = uid
                    db.collection("users").document(uid!!).set(users)
                    result(Resource.Success("Success"))
                } else {
                    result(Resource.Error(it.exception?.message.toString()))
                }
            }
            .addOnFailureListener {
                result(
                    Resource.Error(
                        it.message.toString()
                    )
                )
            }
    }

    override suspend fun loginUser(
        email: String,
        password: String,
        result: (Resource<String>) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    if (auth.currentUser?.isEmailVerified!!) {
                        result(Resource.Success("Success"))
                    } else {
                        result(Resource.Error("Please verify your email"))
                    }
                } else {
                    result(
                        Resource.Error(
                            it.exception?.message.toString()
                        )
                    )
                }
            }
            .addOnFailureListener {
                result(
                    Resource.Error(
                        it.message.toString()
                    )
                )
            }
    }

    override suspend fun updateUser(users: Users, result: (Resource<String>) -> Unit) {
        val document = db.collection("users").document(users.id!!)
        document
            .set(users)
            .addOnSuccessListener {
                result.invoke(
                    Resource.Success("Details Updated Successfully")
                )
            }
            .addOnFailureListener {
                result.invoke(
                    Resource.Error(it.message.toString())
                )
            }
    }

    override suspend fun forgotPassword(email: String, result: (Resource<String>) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    result(
                        Resource.Success("Email Sent")
                    )
                } else {
                    result(
                        Resource.Error(it.exception?.message.toString())
                    )
                }
            }
            .addOnFailureListener {
                result(
                    Resource.Error(it.message.toString())
                )
            }
    }

    override suspend fun logout(result: () -> Unit) {
        auth.signOut()
        result.invoke()
    }
}