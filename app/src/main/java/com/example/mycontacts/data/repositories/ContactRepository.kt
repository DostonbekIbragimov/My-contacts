package com.example.mycontacts.data.repositories

import com.example.mycontacts.app.App
import com.example.mycontacts.contracts.ContactContract
import com.example.mycontacts.data.preferences.LocalStorage
import com.example.mycontacts.data.source.room.AppDatabase
import com.example.mycontacts.data.source.room.entity.ContactData

class ContactRepository:ContactContract.Model {
    private val db = AppDatabase.getDatabase(App.instance)
    private val contactDao = db.contactDao()
    override fun getAll(): List<ContactData>  = contactDao.getAll()

    override fun filterCourseById() =
        contactDao.filterCourseById(LocalStorage.instance.activeUser.id)

    override fun delete(data: ContactData) {
        contactDao.delete(data)
    }

    override fun update(data: ContactData) {
        contactDao.update(data)
    }

    override fun insert(data: ContactData) {
        contactDao.insert(data)
    }

    override fun deleteAll() {
        contactDao.deleteAll(getAll())
    }
}