package com.example.roomdemo.screens

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
fun ContactGroupsScreen(navController: NavController, viewModel: ContactViewModel, contactId: Int) {
    val groups by viewModel.getGroupsForContact(contactId).collectAsState(initial = null)
    val isLoading = groups == null

    Scaffold(topBar = { TopAppBar(title = { Text("Groups for Contact") }) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            when {
                isLoading -> CircularProgressIndicator()
                groups.isNullOrEmpty() -> Text("This contact is not in any group.")
                else -> {
                    groups!!.forEach { group ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Group Name: ${group.name}")
                                Text("Group ID: ${group.groupId}")
                            }
                        }
                    }
                }
            }
        }
    }
}