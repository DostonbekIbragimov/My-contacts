package com.example.mycontacts.contracts

import com.example.mycontacts.data.source.room.dao.LoginPassword

interface RegisterContract {
    interface Model {
        fun addUser(
            fullName: String,
            login: String,
            password: String
        )

        fun getUsers(): List<LoginPassword>
    }

    interface View {
        fun getFullName(): String
        fun getLogin(): String
        fun getPassword(): String
        fun getConfirmPassword(): String
        fun makeToast(str: String)
        fun createAccount()
        fun clear()
    }

    interface Presenter {
        fun clickOnRegisterItem()
    }
}