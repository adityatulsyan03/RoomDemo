package com.example.roomdemo.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.roomdemo.viewmodel.ContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactToGroupScreen(
    navController: NavController,
    viewModel: ContactViewModel,
    groupId: Int
) {
    val contacts by viewModel.getContactsNotInGroup(groupId).collectAsState(initial = null)
    val isLoading = contacts == null

    Scaffold(
        topBar = { TopAppBar(title = { Text("Add Contact to Group") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            when {
                isLoading -> CircularProgressIndicator()
                contacts.isNullOrEmpty() -> Text("No contacts to add for this group.")
                else -> {
                    contacts!!.forEach { contact ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    viewModel.insertGroupContactCrossRef(contact.contactId, groupId)
                                }
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text("Name: ${contact.name}")
                                Text("ID: ${contact.contactId}")
                            }
                        }
                    }
                }
            }
        }
    }
}