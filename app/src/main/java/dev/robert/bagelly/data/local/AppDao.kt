package dev.robert.bagelly.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.flow.Flow


/*
@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM user_table WHERE id = :id")
    suspend fun getUser(id: String) : UserEntity

    @Query("SELECT * FROM sell")
     fun getSellItems() : Flow<List<Sell>>

    @Query("SELECT * FROM sell WHERE itemName LIKE :searchString")
   fun searchItem(searchString: String) : Flow<List<Sell>>

    @Query("SELECT * FROM shop")
     fun getShops() : Flow<List<Shop>>

    @Query("SELECT * FROM posts")
     fun getPosts() : Flow<List<Post>>

}*/
