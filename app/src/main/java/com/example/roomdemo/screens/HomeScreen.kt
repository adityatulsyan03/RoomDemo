package com.example.roomdemo.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.roomdemo.viewmodel.ContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: ContactViewModel) {
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
            Button(onClick = { viewModel.clearDatabase() }) { Text("Clear Database") }
            Button(onClick = { navController.navigate("contact_delete") }) { Text("Go to Contact Deletion") }
            Button(onClick = { navController.navigate("group_delete") }) { Text("Go to Group Deletion") }
        }
    }
}