package com.example.mycontacts.data.preferences

import android.content.Context
import com.example.mycontacts.data.source.room.dao.ActiveUser

class LocalStorage private constructor(context: Context) {
    companion object {
        lateinit var instance: LocalStorage; private set

        fun init(context: Context) {
            instance =
                LocalStorage(context)
        }
    }

    private val pref = context.getSharedPreferences("dostonbek", Context.MODE_PRIVATE)

    var activeUser: ActiveUser
        set(value) {
            val e = pref.edit()
            e.putLong("ID", value.id)
            e.putString("LOGIN", value.login)
            e.putString("PASSWORD", value.password)
            e.apply()
        }
        get() = ActiveUser(
            pref.getLong("ID", -1),
            pref.getString("LOGIN", "").toString(),
            pref.getString("PASSWORD", "").toString()
        )

    fun clear() {
        pref.edit().clear().apply()
    }
}