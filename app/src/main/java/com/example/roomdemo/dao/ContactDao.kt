package com.example.roomdemo.dao

import androidx.room.*
import com.example.roomdemo.entities.Contact
import com.example.roomdemo.entities.ContactAge
import com.example.roomdemo.entities.PhoneNumber
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContactAge(contactAge: ContactAge)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoneNumber(phoneNumber: PhoneNumber)

    @Query("SELECT * FROM contact_table")
    fun getAllContacts(): Flow<List<Contact>>

    @Query("SELECT * FROM contact_age_table WHERE contactId = :contactId")
    fun getContactAge(contactId: Int): Flow<ContactAge?>

    @Query("SELECT * FROM phone_number_table WHERE contactId = :contactId")
    fun getPhoneNumbers(contactId: Int): Flow<List<PhoneNumber>>
}