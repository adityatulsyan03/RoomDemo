package com.example.roomdemo.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//One-to-Many with Contact

@Entity(
    tableName = "phone_number_table",
    foreignKeys = [
        ForeignKey(
            entity = Contact::class,
            parentColumns = ["id"],
            childColumns = ["contactId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PhoneNumber(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val contactId: Int,
    val number: String
)