package com.example.roomdemo.entities

import androidx.room.Entity

//Many-to-Many Junction Table

@Entity(primaryKeys = ["contactId", "groupId"])
data class GroupContactCrossRef(
    val contactId: Int,
    val groupId: Int
)