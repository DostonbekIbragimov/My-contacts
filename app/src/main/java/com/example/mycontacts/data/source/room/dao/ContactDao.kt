package com.example.mycontacts.data.source.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mycontacts.data.source.room.entity.ContactData

@Dao
interface ContactDao : BaseDao<ContactData> {
    @Query("SELECT * FROM ContactData")
    fun getAll(): List<ContactData>

    @Query("SELECT * FROM contactData cr WHERE cr.userId=:userId")
    fun filterCourseById(userId: Long): List<ContactData>

    @Query("DELETE FROM contactData WHERE userId=:id")
    fun deleteById(id: Long)
}