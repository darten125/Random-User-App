package com.example.randomuser.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.randomuser.data.local.dao.UserDao
import com.example.randomuser.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class RandomUserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}