package com.example.mycontacts.data.repositories

import com.example.mycontacts.app.App
import com.example.mycontacts.contracts.RegisterContract
import com.example.mycontacts.data.source.room.AppDatabase
import com.example.mycontacts.data.source.room.dao.LoginPassword
import com.example.mycontacts.data.source.room.entity.UserData

class RegisterRepository : RegisterContract.Model {
    private val db = AppDatabase.getDatabase(App.instance)
    private val userDao = db.userDao()

    override fun addUser(fullName: String, login: String, password: String) {
        userDao.insert(UserData(fullName = fullName, login = login, password = password))
    }

    override fun getUsers(): List<LoginPassword> = userDao.getLoginPassword()
}