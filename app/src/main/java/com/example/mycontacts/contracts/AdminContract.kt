package com.example.mycontacts.contracts

import com.example.mycontacts.data.source.room.dao.LoginPassword
import com.example.mycontacts.data.source.room.entity.UserData

interface AdminContract {
    interface Model {
        fun getAll(): List<UserData>
        fun getLoginPasswords():List<LoginPassword>
        fun setActiveUser(data: UserData)
        fun delete(data: UserData)
        fun edit(data: UserData)
        fun insert(data: UserData)
    }

    interface View {
        fun loadData(list: List<UserData>)
        fun openContactActivity()
        fun removeItem(data: UserData)
        fun updateItem(data: UserData)
        fun insertItem(data: UserData)
        fun openEditDialog(data: UserData)
        fun openAddDialog()
        fun makeToast(txt: String)
    }

    interface Presenter{
        fun clickUser(data: UserData)
        fun deleteUser(data: UserData)
        fun addUser(data: UserData)
        fun editUser(data: UserData)
        fun confirmEditUser(data: UserData)
        fun openAddUser()
    }
}