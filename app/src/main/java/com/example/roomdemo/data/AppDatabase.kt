package com.example.roomdemo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomdemo.dao.ContactDao
import com.example.roomdemo.dao.GroupDao
import com.example.roomdemo.entities.Contact
import com.example.roomdemo.entities.ContactAge
import com.example.roomdemo.entities.Group
import com.example.roomdemo.entities.GroupContactCrossRef
import com.example.roomdemo.entities.PhoneNumber

@Database(
    entities = [Contact::class, ContactAge::class, PhoneNumber::class, Group::class, GroupContactCrossRef::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
    abstract fun groupDao(): GroupDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
