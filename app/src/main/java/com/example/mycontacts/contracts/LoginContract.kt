package com.example.mycontacts.contracts

import android.os.Bundle
import com.example.mycontacts.data.source.room.dao.ActiveUser
import com.example.mycontacts.data.source.room.dao.LoginPassword

interface LoginContract {
    interface Model{
        fun getLoginPassword() : List<LoginPassword>
        fun getUser(login: String, password: String):ActiveUser
    }
    interface View {
        fun makeToast(txt: String)
        fun getLogin():String
        fun getPassword():String
        fun loginToEditable(login:String)
        fun passwordToEditable(password:String)
        fun openAdminActivity()
        fun openContactActivity()
        fun clear()
    }

    interface Presenter{
        fun getBundle(bundle: Bundle)
        fun login()
    }
}