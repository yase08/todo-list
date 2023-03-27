package com.example.nyobakotlin.data.dao

import androidx.room.*
import com.example.nyobakotlin.data.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM USER WHERE uid = :uid")
    fun get(uid: Int) : User

    @Update
    fun update(user: User)
}