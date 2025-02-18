package com.example.roomdemo.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.roomdemo.viewmodel.ContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactToGroupScreen(navController: NavController, viewModel: ContactViewModel, groupId: Int) {
    val contacts by viewModel.getContactsNotInGroup(groupId).collectAsState(emptyList())

    Scaffold(
        topBar = { TopAppBar(title = { Text("Add Contact to Group") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (contacts.isEmpty()){
                Text("No contacts to add for this group.")
            }
            contacts.forEach { contact ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            viewModel.insertGroupContactCrossRef(contact.id, groupId)
                        }
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Name: ${contact.name}")
                        Text("ID: ${contact.id}")
                    }
                }
            }
        }
    }
}