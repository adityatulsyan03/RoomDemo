package com.example.roomdemo.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

// One-to-Many with Contact
@Entity(
    tableName = "phone_number_table",
    foreignKeys = [
        ForeignKey(
            entity = Contact::class,
            parentColumns = ["contactId"],
            childColumns = ["contactId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["contactId"])]
)
data class PhoneNumber(
    @PrimaryKey(autoGenerate = true) val phoneId: Int = 0,
    val contactId: Int,
    val number: String
)