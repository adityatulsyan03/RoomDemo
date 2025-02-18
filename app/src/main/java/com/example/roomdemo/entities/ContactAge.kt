package com.example.roomdemo.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//One-to-One with Contact

@Entity(
    tableName = "contact_age_table",
    foreignKeys = [
        ForeignKey(
            entity = Contact::class,
            parentColumns = ["id"],
            childColumns = ["contactId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ContactAge(
    @PrimaryKey val contactId: Int,
    val age: Int
)