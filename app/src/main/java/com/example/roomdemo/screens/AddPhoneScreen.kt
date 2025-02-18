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
fun AddPhoneScreen(navController: NavController, viewModel: ContactViewModel, contactId: Int) {
    var phoneNumber by remember { mutableStateOf("") }
    val phoneNumbers by viewModel.getPhoneNumbers(contactId).collectAsState(initial = emptyList())

    Scaffold(
        topBar = { TopAppBar(title = { Text("Add Phone Number") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (phoneNumber.isNotBlank()) {
                    viewModel.insertPhoneNumber(contactId, phoneNumber)
                    phoneNumber = ""  // Clear input field
                }
            }) { Text("Save Phone Number") }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Saved Phone Numbers:")
            phoneNumbers.forEach { phone ->
                Text(text = phone.number, modifier = Modifier.padding(4.dp))
            }
        }
    }
}