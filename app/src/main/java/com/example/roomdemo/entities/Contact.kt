package com.example.roomdemo.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

//One-to-One with ContactAge, One-to-Many with PhoneNumber

@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)
