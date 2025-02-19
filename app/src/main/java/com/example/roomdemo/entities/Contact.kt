package com.example.roomdemo.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// One-to-One with ContactAge, One-to-Many with PhoneNumber
@Entity(
    tableName = "contact_table",
    indices = [Index(value = ["name"], unique = false)]
)
data class Contact(
    @PrimaryKey(autoGenerate = true) val contactId: Int = 0,
    val name: String
)