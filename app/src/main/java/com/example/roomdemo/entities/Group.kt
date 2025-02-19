package com.example.roomdemo.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// Many-to-Many with Contact via GroupContactCrossRef
@Entity(
    tableName = "group_table",
    indices = [Index(value = ["name"], unique = true)]
)
data class Group(
    @PrimaryKey(autoGenerate = true) val groupId: Int = 0,
    val name: String
)