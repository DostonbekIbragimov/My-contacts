package com.example.mycontacts.data.repositories

import com.example.mycontacts.app.App
import com.example.mycontacts.contracts.AdminContract
import com.example.mycontacts.data.preferences.LocalStorage
import com.example.mycontacts.data.source.room.AppDatabase
import com.example.mycontacts.data.source.room.dao.LoginPassword
import com.example.mycontacts.data.source.room.entity.UserData

class AdminRepository: AdminContract.Model {
    private val db = AppDatabase.getDatabase(App.instance)
    private val userDao = db.userDao()
    private val contactDao = db.contactDao()
    override fun getAll(): List<UserData> = userDao.getAll()
    override fun getLoginPasswords(): List<LoginPassword>  = userDao.getLoginPassword()

    override fun setActiveUser(data: UserData) {
        LocalStorage.instance.activeUser = userDao.getUser(data.login, data.password)
    }

    override fun delete(data: UserData) {
        userDao.delete(data)
        contactDao.deleteById(LocalStorage.instance.activeUser.id)
    }

    override fun edit(data: UserData) {
        userDao.update(data)
    }

    override fun insert(data: UserData) {
        userDao.insert(data)
    }

}