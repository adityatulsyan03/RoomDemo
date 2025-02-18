package com.example.roomdemo.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.roomdemo.viewmodel.ContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupDetailsScreen(navController: NavController, viewModel: ContactViewModel, groupId: Int) {
    val contacts by viewModel.getContactsInGroup(groupId).collectAsState(initial = emptyList())

    Scaffold(
        topBar = { TopAppBar(title = { Text("Group Details") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (contacts.isEmpty()) {
                Text("No contacts in this group.")
            } else {
                contacts.forEach { contact ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Name: ${contact.name}")
                            Text("ID: ${contact.id}")
                            Text("Group ID: $groupId")
                        }
                    }
                }
            }
        }
    }
}