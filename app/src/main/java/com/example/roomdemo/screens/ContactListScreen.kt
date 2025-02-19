package com.example.roomdemo.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
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
fun ContactListScreen(navController: NavController, viewModel: ContactViewModel) {
    val contacts by viewModel.contacts.collectAsState(initial = emptyList())
    val contactAges by viewModel.contactAges.collectAsState(initial = emptyMap())

    val isLoading = contacts.isEmpty() || contactAges.isEmpty()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Contacts") }) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            item {
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    contacts.forEach { contact ->
                        val age = contactAges[contact.contactId]

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable { navController.navigate("add_phone/${contact.contactId}") }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Name: ${contact.name}")
                                Text("ID: ${contact.contactId}")
                                Text("Age: ${age ?: "N/A"}")
                                Button(onClick = { navController.navigate("contact_groups/${contact.contactId}") }) {
                                    Text("View Contact Groups")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}