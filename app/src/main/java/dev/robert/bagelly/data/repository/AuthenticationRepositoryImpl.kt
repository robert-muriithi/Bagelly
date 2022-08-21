package dev.robert.bagelly.data.repository

import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.robert.bagelly.model.Users
import dev.robert.bagelly.utils.FirestoreCollections
import dev.robert.bagelly.utils.Resource
import javax.inject.Inject

class AuthenticationRepositoryImpl
@Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    @ApplicationContext private val application: Application,
    private val preferences: SharedPreferences
) : AuthenticationRepository {
    private  val TAG = "AuthenticationRepositoryImpl"
    companion object {
        val firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
    }

    init {
        db.firestoreSettings = firestoreSettings
    }
    @SuppressLint("LongLogTag")
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
                    db.collection(FirestoreCollections.UserCollection).document(uid!!).set(users)
                    preferences.edit()
                        .putString("user_id", uid)
                        .putString("user_email", email)
                        .putString("user_name", users.name)
                        .putString("user_phone", users.phoneNumber)
                        .putString("user_loc", users.location)
                        .putString("user_image", users.profileImageUrl)
                        .apply()

                    Log.d(TAG, "registerUser: ${preferences.all}")
                    result(Resource.Success("Success"))
                    Toast.makeText(application.applicationContext, "User created Successfully", Toast.LENGTH_SHORT).show()
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
                        Toast.makeText(application.applicationContext, "Please verify your email", Toast.LENGTH_SHORT).show()
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
        val document = db.collection(FirestoreCollections.UserCollection).document(users.id!!)
        document
            .set(users)
            .addOnSuccessListener {
                Toast.makeText(application.applicationContext, "Details Updated Successfully", Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(application.applicationContext, "Email Sent", Toast.LENGTH_SHORT).show()

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