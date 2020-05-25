package com.example.mycontacts.contracts

import com.example.mycontacts.data.source.room.entity.ContactData

interface ContactContract {
    interface Model {
        fun getAll(): List<ContactData>
        fun filterCourseById(): List<ContactData>
        fun delete(data: ContactData)
        fun update(data: ContactData)
        fun insert(data: ContactData)
        fun deleteAll()
    }

    interface View {
        fun loadData(list: List<ContactData>)
        fun makeToast(txt: String)
        fun deleteItem(data: ContactData)
        fun updateItem(data: ContactData)
        fun insertItem(data: ContactData)
        fun removeAllItem()
        fun openEditDialog(data: ContactData)
        fun openInsertDialog()
    }

    interface Presenter{
        fun deleteContact(data: ContactData)
        fun editContact(data: ContactData)
        fun confirmEditContact(data: ContactData)
        fun createContact(data: ContactData)
        fun openAddContact()
        fun deleteAll()
    }
}