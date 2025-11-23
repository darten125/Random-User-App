package com.example.randomuser.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.randomuser.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM users ORDER BY timestamp DESC")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE uuid = :id LIMIT 1")
    fun getUserById(id: String): Flow<UserEntity>

    @Query("DELETE FROM users WHERE uuid = :id")
    suspend fun deleteUserById(id: String)

    @Query("DELETE FROM users")
    suspend fun clearAllUsers()
}