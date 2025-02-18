package com.example.roomdemo.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Home") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(onClick = { navController.navigate("add_contact") }) { Text("Add Contact") }
            Button(onClick = { navController.navigate("contact_list") }) { Text("View Contacts") }
            Button(onClick = { navController.navigate("add_group") }) { Text("Add Group") }
            Button(onClick = { navController.navigate("group_list") }) { Text("View Groups") }
            Button(onClick = { navController.navigate("contact_group") }) { Text("Manage Group Contacts") }
        }
    }
}