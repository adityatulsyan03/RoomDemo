package com.example.roomdemo.dao

import androidx.room.*
import com.example.roomdemo.entities.Contact
import com.example.roomdemo.entities.ContactAge
import com.example.roomdemo.entities.PhoneNumber
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Query("SELECT * FROM contact_table WHERE name = :name LIMIT 1")
    suspend fun getContactByName(name: String): Contact?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContactAge(contactAge: ContactAge)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoneNumber(phoneNumber: PhoneNumber)

    @Query("SELECT * FROM contact_table ORDER BY name ASC")
    fun getAllContacts(): Flow<List<Contact>>

    @Query("SELECT * FROM contact_age_table")
    fun getAllContactAges(): Flow<List<ContactAge>>

    @Query("SELECT * FROM contact_age_table WHERE contactId = :contactId LIMIT 1")
    fun getContactAge(contactId: Int): Flow<ContactAge?>

    @Query("SELECT * FROM phone_number_table WHERE contactId = :contactId ORDER BY number ASC")
    fun getPhoneNumbers(contactId: Int): Flow<List<PhoneNumber>>

    @Query("DELETE FROM contact_age_table WHERE contactId = :contactId")
    suspend fun deleteContactAge(contactId: Int)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("DELETE FROM phone_number_table WHERE contactId = :contactId AND number = :number")
    suspend fun deletePhoneNumber(contactId: Int,number: String)

    @Query("DELETE FROM phone_number_table WHERE contactId = :contactId")
    suspend fun deleteAllPhoneNumbers(contactId: Int)

}