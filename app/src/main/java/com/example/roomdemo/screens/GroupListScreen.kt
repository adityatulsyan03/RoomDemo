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
fun GroupListScreen(navController: NavController, viewModel: ContactViewModel) {
    val groups by viewModel.groups.collectAsState(initial = emptyList())

    Scaffold(
        topBar = { TopAppBar(title = { Text("Groups") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            groups.forEach { group ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            navController.navigate("group_details/${group.id}")
                        }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Group Name: ${group.name}")
                        Text("Group ID: ${group.id}")
                    }
                }
            }
        }
    }
}