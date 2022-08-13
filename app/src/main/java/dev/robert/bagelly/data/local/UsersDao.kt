package dev.robert.bagelly.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.google.firebase.firestore.auth.User

@Dao
interface UsersDao {
    @Insert
    suspend fun insert(user: User)
    @Query("SELECT * FROM user_table WHERE id = :id")
    suspend fun getUser(id: String): User
}