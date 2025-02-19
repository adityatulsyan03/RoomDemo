package com.example.roomdemo.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.roomdemo.data.AppDatabase
import com.example.roomdemo.entities.Contact
import com.example.roomdemo.entities.ContactAge
import com.example.roomdemo.entities.Group
import com.example.roomdemo.entities.GroupContactCrossRef
import com.example.roomdemo.entities.PhoneNumber
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val contactDao = db.contactDao()
    private val groupDao = db.groupDao()
    private val databaseDao = db.databaseDao()

    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts: StateFlow<List<Contact>> = _contacts.asStateFlow()

    private val _groups = MutableStateFlow<List<Group>>(emptyList())
    val groups: StateFlow<List<Group>> = _groups.asStateFlow()

    init {
        fetchContacts()
        fetchGroups()
        fetchContactAges()
    }

    private fun fetchContacts() {
        contactDao.getAllContacts()
            .catch { e -> Log.e("ContactViewModel", "Error fetching contacts: ${e.message}", e) }
            .onEach { _contacts.value = it }
            .launchIn(viewModelScope)
    }

    private fun fetchGroups() {
        groupDao.getAllGroups()
            .catch { e -> Log.e("ContactViewModel", "Error fetching groups: ${e.message}", e) }
            .onEach { _groups.value = it }
            .launchIn(viewModelScope)
    }

    private val _contactsInGroup = MutableStateFlow<Map<Int, List<Contact>>>(emptyMap())

    fun getContactsInGroup(groupId: Int): StateFlow<List<Contact>> {
        if (!_contactsInGroup.value.containsKey(groupId)) {
            groupDao.getContactsInGroup(groupId)
                .catch { e ->
                    Log.e(
                        "ContactViewModel",
                        "Error fetching contacts in group: ${e.message}",
                        e
                    )
                }
                .onStart { _contactsInGroup.value += (groupId to emptyList()) }
                .onEach { _contactsInGroup.value += (groupId to it) }
                .launchIn(viewModelScope)
        }
        return _contactsInGroup.map { it[groupId] ?: emptyList() }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    private val _contactsNotInGroup = MutableStateFlow<Map<Int, List<Contact>>>(emptyMap())

    fun getContactsNotInGroup(groupId: Int): StateFlow<List<Contact>> {
        if (!_contactsNotInGroup.value.containsKey(groupId)) {
            groupDao.getContactsNotInGroup(groupId)
                .catch { e ->
                    Log.e(
                        "ContactViewModel",
                        "Error fetching contacts not in group: ${e.message}",
                        e
                    )
                }
                .onStart { _contactsNotInGroup.value += (groupId to emptyList()) }
                .onEach { _contactsNotInGroup.value += (groupId to it) }
                .launchIn(viewModelScope)
        }
        return _contactsNotInGroup.map { it[groupId] ?: emptyList() }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    private val _groupsForContact = MutableStateFlow<Map<Int, List<Group>>>(emptyMap())

    fun getGroupsForContact(contactId: Int): StateFlow<List<Group>> {
        if (!_groupsForContact.value.containsKey(contactId)) {
            groupDao.getGroupsForContact(contactId)
                .catch { e ->
                    Log.e(
                        "ContactViewModel",
                        "Error fetching groups for contact: ${e.message}",
                        e
                    )
                }
                .onStart { _groupsForContact.value += (contactId to emptyList()) }
                .onEach { _groupsForContact.value += (contactId to it) }
                .launchIn(viewModelScope)
        }
        return _groupsForContact.map { it[contactId] ?: emptyList() }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    private val _contactAges = MutableStateFlow<Map<Int, Int?>>(emptyMap())
    val contactAges: StateFlow<Map<Int, Int?>> = _contactAges.asStateFlow()

    private fun fetchContactAges() {
        viewModelScope.launch {
            contactDao.getAllContactAges()
                .collect { contactAgeList ->
                    _contactAges.value = contactAgeList.associate { it.contactId to it.age }
                }
        }
    }

    fun getContactAge(contactId: Int): Flow<ContactAge?> =
        contactDao.getContactAge(contactId)
            .catch { e ->
                Log.e("ContactViewModel", "Error fetching contact age: ${e.message}", e)
                emit(null)
            }

    fun getPhoneNumbers(contactId: Int): Flow<List<PhoneNumber>> =
        contactDao.getPhoneNumbers(contactId)
            .onStart { emit(emptyList()) }
            .catch { e ->
                Log.e("ContactViewModel", "Error fetching phone numbers: ${e.message}", e)
                emit(emptyList())
            }

    fun insertContact(name: String, age: Int?) = viewModelScope.launch {
        try {
            val contactId = contactDao.insertContact(Contact(name = name)).toInt()
            if (age != null) {
                contactDao.insertContactAge(ContactAge(contactId, age))
            }
            fetchContacts()
        } catch (e: Exception) {
            Log.e("ContactViewModel", "Error inserting contact: ${e.message}", e)
        }
    }

    fun insertPhoneNumber(contactId: Int, number: String) = viewModelScope.launch {
        try {
            contactDao.insertPhoneNumber(PhoneNumber(contactId = contactId, number = number))
        } catch (e: Exception) {
            Log.e("ContactViewModel", "Error inserting phone number: ${e.message}", e)
        }
    }

    fun insertGroup(name: String) = viewModelScope.launch {
        try {
            groupDao.insertGroup(Group(name = name))
            fetchGroups()
        } catch (e: Exception) {
            Log.e("ContactViewModel", "Error inserting group: ${e.message}", e)
        }
    }

    fun insertGroupContactCrossRef(contactId: Int, groupId: Int) = viewModelScope.launch {
        try {
            groupDao.insertGroupContactCrossRef(GroupContactCrossRef(contactId, groupId))
        } catch (e: Exception) {
            Log.e("ContactViewModel", "Error inserting contact-group relation: ${e.message}", e)
        }
    }

    fun clearDatabase() = viewModelScope.launch {
        try {
            databaseDao.clearDatabase()
            _contacts.value = emptyList()
            _groups.value = emptyList()
            _contactsInGroup.value = emptyMap()
            _contactsNotInGroup.value = emptyMap()
            _groupsForContact.value = emptyMap()
            Log.d("Database", "All tables cleared successfully")
        } catch (e: Exception) {
            Log.e("Database", "Error clearing database: ${e.message}")
        }
    }

    fun deleteContact(name: String) = viewModelScope.launch {
        try {
            val contact = contactDao.getContactByName(name = name)
            if (contact != null) {
                contactDao.deleteContactAge(contact.contactId)
                contactDao.deleteAllPhoneNumbers(contact.contactId)
                contactDao.deleteContact(contact)
                Log.d("ContactViewModel", "Deleted contact: $name")
                fetchContacts()
            } else {
                Log.e("ContactViewModel", "Contact not found: $name")
            }
        } catch (e: Exception) {
            Log.e("ContactViewModel", "Error deleting contact: ${e.message}", e)
        }
    }

    fun deletePhoneNumber(name: String, number: String) = viewModelScope.launch {
        try {
            val contact = contactDao.getContactByName(name)
            if (contact != null) {
                contactDao.deletePhoneNumber(contact.contactId, number)
                Log.d("ContactViewModel", "Deleted phone number $number for contact: $name")
            } else {
                Log.e("ContactViewModel", "Error: Contact not found with name: $name")
            }
        } catch (e: Exception) {
            Log.e("ContactViewModel", "Error deleting phone number for $name: ${e.message}", e)
        }
    }

    fun deleteAllPhoneNumbersByName(name: String) = viewModelScope.launch {
        try {
            val contact = contactDao.getContactByName(name)
            if (contact != null) {
                contactDao.deleteAllPhoneNumbers(contact.contactId)
                Log.d("ContactViewModel", "Deleted all phone numbers for: $name")
            } else {
                Log.e("ContactViewModel", "Contact not found: $name")
            }
        } catch (e: Exception) {
            Log.e("ContactViewModel", "Error deleting phone numbers by name: ${e.message}", e)
        }
    }

    fun deleteGroupByName(name: String) = viewModelScope.launch {
        try {
            val group = groupDao.getGroupByName(name)
            if (group != null) {
                groupDao.deleteAllContactInGroup(group.groupId)
                groupDao.deleteGroup(group.groupId)
                fetchGroups()
                Log.d("ContactViewModel", "Deleted group: $name")
            } else {
                Log.e("ContactViewModel", "Group not found: $name")
            }
        } catch (e: Exception) {
            Log.e("ContactViewModel", "Error deleting group: ${e.message}", e)
        }
    }

    fun deleteContactFromGroup(contactName: String, groupName: String) = viewModelScope.launch {
        try {
            val contact = contactDao.getContactByName(contactName)
            val group = groupDao.getGroupByName(groupName)
            if (contact != null && group != null) {
                groupDao.deleteContactInGroup(contact.contactId, group.groupId)
                Log.d("ContactViewModel", "Removed $contactName from $groupName")
            } else {
                Log.e("ContactViewModel", "Error: Contact or Group not found")
            }
        } catch (e: Exception) {
            Log.e("ContactViewModel", "Error removing contact from group: ${e.message}", e)
        }
    }

    fun deleteAllContactsInGroup(name: String) = viewModelScope.launch {
        try {
            val group = groupDao.getGroupByName(name)
            if (group != null) {
                groupDao.deleteAllContactInGroup(group.groupId)
                Log.d("ContactViewModel", "Removed all contacts from group: $name")
            } else {
                Log.e("ContactViewModel", "Group not found: $name")
            }
        } catch (e: Exception) {
            Log.e("ContactViewModel", "Error removing all contacts from group: ${e.message}", e)
        }
    }
}

class ContactViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}