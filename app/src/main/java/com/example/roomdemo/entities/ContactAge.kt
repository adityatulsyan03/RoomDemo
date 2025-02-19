package com.example.roomdemo.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

// One-to-One with Contact
@Entity(
    tableName = "contact_age_table",
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
data class ContactAge(
    @PrimaryKey val contactId: Int,
    val age: Int
)