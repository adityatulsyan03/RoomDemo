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
fun AddContactScreen(navController: NavController, viewModel: ContactViewModel) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Add Contact") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("Age") },
                isError = errorMessage != null
            )
            if (errorMessage != null) {
                Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val ageInt = age.toIntOrNull()
                if (name.isBlank()) {
                    errorMessage = "Name cannot be empty"
                } else if (ageInt == null || ageInt <= 0) {
                    errorMessage = "Enter a valid age"
                } else {
                    viewModel.insertContact(name, ageInt)
                    navController.popBackStack()
                }
            }) {
                Text("Save Contact")
            }
        }
    }
}