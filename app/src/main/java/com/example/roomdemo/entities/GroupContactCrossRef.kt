package com.example.roomdemo.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

// Many-to-Many Junction Table
@Entity(
    primaryKeys = ["contactId", "groupId"],
    foreignKeys = [
        ForeignKey(
            entity = Contact::class,
            parentColumns = ["contactId"],
            childColumns = ["contactId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Group::class,
            parentColumns = ["groupId"],
            childColumns = ["groupId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["contactId"]),
        Index(value = ["groupId"])
    ]
)
data class GroupContactCrossRef(
    val contactId: Int,
    val groupId: Int
)