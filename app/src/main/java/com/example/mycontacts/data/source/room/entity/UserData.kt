package com.example.mycontacts.data.source.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserData(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var fullName: String = "",
    var login: String = "",
    var password: String = ""
)