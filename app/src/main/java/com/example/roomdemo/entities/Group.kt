package com.example.roomdemo.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

//Many-to-Many with Contact via GroupContactCrossRef

@Entity(tableName = "group_table")
data class Group(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)