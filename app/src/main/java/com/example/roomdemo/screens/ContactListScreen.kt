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
fun ContactListScreen(navController: NavController, viewModel: ContactViewModel) {
    val contacts by viewModel.contacts.collectAsState(initial = emptyList())

    Scaffold(
        topBar = { TopAppBar(title = { Text("Contacts") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            contacts.forEach { contact ->
                val age by viewModel.getContactAge(contact.id).collectAsState(initial = null)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { navController.navigate("add_phone/${contact.id}") }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Name: ${contact.name}")
                        Text("ID: ${contact.id}")
                        Text("Age: ${age?.age ?: "N/A"}")
                        Button(onClick = { navController.navigate("contact_groups/${contact.id}") }) {
                            Text("View Contact Groups")
                        }
                    }
                }
            }
        }
    }
}