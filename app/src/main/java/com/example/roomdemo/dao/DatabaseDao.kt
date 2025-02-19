package com.example.roomdemo.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface DatabaseDao {
    @Query("DELETE FROM contact_table")
    suspend fun deleteAllContacts()

    @Query("DELETE FROM contact_age_table")
    suspend fun deleteAllContactAges()

    @Query("DELETE FROM phone_number_table")
    suspend fun deleteAllPhoneNumbers()

    @Query("DELETE FROM group_table")
    suspend fun deleteAllGroups()

    @Query("DELETE FROM GroupContactCrossRef")
    suspend fun deleteAllGroupContacts()

    @Transaction
    suspend fun clearDatabase() {
        deleteAllGroupContacts()
        deleteAllGroups()
        deleteAllPhoneNumbers()
        deleteAllContactAges()
        deleteAllContacts()
    }
}