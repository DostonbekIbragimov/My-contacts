package com.example.mycontacts.data.source.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ContactData(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String = "",
    var email: String = "",
    var userId: Long = 0
)