package dev.robert.bagelly.data.repository

import android.app.Application
import android.widget.Toast
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.robert.bagelly.model.Users
import dev.robert.bagelly.utils.Resource
import javax.inject.Inject

class AuthenticationRepositoryImpl
@Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    @ApplicationContext private val application: Application
) : AuthenticationRepository {
    override suspend fun register(
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
    }
}