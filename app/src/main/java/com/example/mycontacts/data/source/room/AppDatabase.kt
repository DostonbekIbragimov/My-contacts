package com.example.mycontacts.data.source.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mycontacts.data.source.room.dao.ContactDao
import com.example.mycontacts.data.source.room.dao.UserDao
import com.example.mycontacts.data.source.room.entity.ContactData
import com.example.mycontacts.data.source.room.entity.UserData

@Database(
    entities = [UserData::class, ContactData::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun contactDao(): ContactDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}