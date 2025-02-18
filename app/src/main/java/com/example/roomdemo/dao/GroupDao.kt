package com.example.roomdemo.dao

import androidx.room.*
import com.example.roomdemo.entities.Contact
import com.example.roomdemo.entities.Group
import com.example.roomdemo.entities.GroupContactCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: Group): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroupContactCrossRef(crossRef: GroupContactCrossRef)

    @Query("SELECT * FROM group_table")
    fun getAllGroups(): Flow<List<Group>>

    @Query("SELECT * FROM group_table WHERE id IN (SELECT groupId FROM GroupContactCrossRef WHERE contactId = :contactId)")
    fun getGroupsForContact(contactId: Int): Flow<List<Group>>

    @Transaction
    @Query("""
    SELECT * FROM contact_table 
    WHERE id IN (
        SELECT contactId FROM GroupContactCrossRef WHERE groupId = :groupId
    )
    """)
    fun getContactsInGroup(groupId: Int): Flow<List<Contact>>

    @Query("""
    SELECT * FROM contact_table
    WHERE id NOT IN (
        SELECT contactId FROM groupcontactcrossref WHERE groupId = :groupId
    )
    """)
    fun getContactsNotInGroup(groupId: Int): Flow<List<Contact>>

}