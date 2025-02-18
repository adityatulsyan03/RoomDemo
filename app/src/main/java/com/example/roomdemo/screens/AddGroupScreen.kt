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
fun AddGroupScreen(navController: NavController, viewModel: ContactViewModel) {
    var groupName by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Add Group") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = groupName,
                onValueChange = { groupName = it },
                label = { Text("Group Name") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (groupName.isNotBlank()) {
                    viewModel.insertGroup(groupName)
                    groupName = ""  // Clear input field
                    navController.popBackStack() // Go back to the previous screen
                }
            }) { Text("Save Group") }
        }
    }
}