package com.example.mycontacts.data.source.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mycontacts.data.source.room.entity.UserData

@Dao
interface UserDao : BaseDao<UserData> {
    @Query("SELECT * FROM UserData")
    fun getAll(): List<UserData>

    @Query("SELECT fullName, login, password FROM UserData")
    fun getUsersWithoutId(): List<UsersWithOutIdData>

    @Query("SELECT login, password FROM UserData")
    fun getLoginPassword(): List<LoginPassword>

    @Query("SELECT id, login, password FROM userdata WHERE login=:login AND password=:password")
    fun getUser(login: String, password: String): ActiveUser
}

data class ActiveUser(val id: Long, val login: String, val password: String)
data class LoginPassword(val login: String, val password: String)
data class UsersWithOutIdData(val fullName: String, val login: String, val password: String)