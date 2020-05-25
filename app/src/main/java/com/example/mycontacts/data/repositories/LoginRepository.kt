package com.example.mycontacts.data.repositories

import com.example.mycontacts.app.App
import com.example.mycontacts.contracts.LoginContract
import com.example.mycontacts.data.source.room.AppDatabase
import com.example.mycontacts.data.source.room.dao.ActiveUser
import com.example.mycontacts.data.source.room.dao.LoginPassword

class LoginRepository : LoginContract.Model {
    private val db = AppDatabase.getDatabase(App.instance)
    private val userDao = db.userDao()

    override fun getLoginPassword(): List<LoginPassword> = userDao.getLoginPassword()

    override fun getUser(login: String, password: String): ActiveUser =
        userDao.getUser(login, password)
}