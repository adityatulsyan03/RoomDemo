package com.example.roomdemo.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.roomdemo.data.AppDatabase
import com.example.roomdemo.entities.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val contactDao = db.contactDao()
    private val groupDao = db.groupDao()

    // Using StateFlow for Jetpack Compose
    val contacts: StateFlow<List<Contact>> = contactDao.getAllContacts()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val groups: StateFlow<List<Group>> = groupDao.getAllGroups()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Insert Contact with One-to-One relationship (ContactAge)
    fun insertContact(name: String, age: Int) = viewModelScope.launch {
        val contactId = contactDao.insertContact(Contact(name = name)).toInt()
        contactDao.insertContactAge(ContactAge(contactId, age))
    }

    // Insert Phone Number (One-to-Many)
    fun insertPhoneNumber(contactId: Int, number: String) = viewModelScope.launch {
        contactDao.insertPhoneNumber(PhoneNumber(contactId = contactId, number = number))
    }

    // Insert Group (Many-to-Many)
    fun insertGroup(name: String) = viewModelScope.launch {
        groupDao.insertGroup(Group(name = name))
    }

    // Insert Group-Contact Relationship (Many-to-Many)
    fun insertGroupContactCrossRef(contactId: Int, groupId: Int) = viewModelScope.launch {
        groupDao.insertGroupContactCrossRef(GroupContactCrossRef(contactId, groupId))
    }

    // Retrieve Contact's Age (One-to-One)
    fun getContactAge(contactId: Int): Flow<ContactAge?> {
        return contactDao.getContactAge(contactId)
    }

    // Retrieve Phone Numbers for a Contact (One-to-Many)
    fun getPhoneNumbers(contactId: Int): Flow<List<PhoneNumber>> {
        return contactDao.getPhoneNumbers(contactId)
    }

    // Retrieve Groups of a Contact (Many-to-Many)
    fun getGroupsForContact(contactId: Int): Flow<List<Group>> {
        return groupDao.getGroupsForContact(contactId)
    }

    // Retrieve Contacts in a Specific Group (Many-to-Many)
    fun getContactsInGroup(groupId: Int): Flow<List<Contact>> {
        return groupDao.getContactsInGroup(groupId)
    }

    fun getContactsNotInGroup(groupId: Int): Flow<List<Contact>> {
        return groupDao.getContactsNotInGroup(groupId)
    }
}