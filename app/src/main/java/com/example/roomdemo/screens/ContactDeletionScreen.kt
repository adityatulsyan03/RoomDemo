package com.example.roomdemo.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomdemo.viewmodel.ContactViewModel

@Composable
fun ContactDeletionScreen(viewModel: ContactViewModel) {
    var name by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Delete Contact", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter Contact Name") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = number,
            onValueChange = { number = it },
            label = { Text("Enter Phone Number (Optional)") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (name.isNotBlank()) {
                    viewModel.deleteContact(name)
                    Toast.makeText(context, "Deleted Contact: $name", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Please enter a contact name!", Toast.LENGTH_SHORT)
                        .show()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Delete Contact")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (name.isNotBlank() && number.isNotBlank()) {
                    viewModel.deletePhoneNumber(name, number)
                    Toast.makeText(context, "Deleted Phone Number: $number", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context, "Enter both Name & Number!", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("Delete Phone Number")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (name.isNotBlank()) {
                    viewModel.deleteAllPhoneNumbersByName(name)
                    Toast.makeText(
                        context,
                        "Deleted all phone numbers for: $name",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(context, "Please enter a contact name!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        ) {
            Text("Delete All Phone Numbers")
        }
    }
}