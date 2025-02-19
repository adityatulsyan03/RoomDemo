package com.example.roomdemo.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.roomdemo.entities.Contact
import com.example.roomdemo.entities.Group
import com.example.roomdemo.entities.GroupContactCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {

    @Query("SELECT * FROM group_table WHERE name = :name LIMIT 1")
    suspend fun getGroupByName(name: String): Group?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: Group): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroupContactCrossRef(crossRef: GroupContactCrossRef)

    @Query("SELECT * FROM group_table ORDER BY name ASC")
    fun getAllGroups(): Flow<List<Group>>

    @Query(
        """
        SELECT * FROM group_table 
        WHERE groupId IN (SELECT groupId FROM GroupContactCrossRef WHERE contactId = :contactId)
        ORDER BY name ASC
    """
    )
    fun getGroupsForContact(contactId: Int): Flow<List<Group>>

    @Transaction
    @Query(
        """
    SELECT * FROM contact_table 
    WHERE contactId IN (
        SELECT contactId FROM GroupContactCrossRef WHERE groupId = :groupId
    )
    ORDER BY name ASC
    """
    )
    fun getContactsInGroup(groupId: Int): Flow<List<Contact>>

    @Query(
        """
    SELECT * FROM contact_table
    WHERE contactId NOT IN (
        SELECT contactId FROM groupcontactcrossref WHERE groupId = :groupId
    )
    ORDER BY name ASC
    """
    )
    fun getContactsNotInGroup(groupId: Int): Flow<List<Contact>>

    @Query("DELETE FROM group_table WHERE groupId = :groupId")
    suspend fun deleteGroup(groupId: Int)

    @Query("DELETE FROM groupcontactcrossref WHERE contactId = :contactId AND groupId = :groupId")
    suspend fun deleteContactInGroup(contactId: Int, groupId: Int)

    @Query("DELETE FROM groupcontactcrossref WHERE groupId = :groupId")
    suspend fun deleteAllContactInGroup(groupId: Int)

}