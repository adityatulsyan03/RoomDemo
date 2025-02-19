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
fun GroupDeletionScreen(viewModel: ContactViewModel) {
    var groupName by remember { mutableStateOf("") }
    var contactName by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Delete Group", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = groupName,
            onValueChange = { groupName = it },
            label = { Text("Enter Group Name") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = contactName,
            onValueChange = { contactName = it },
            label = { Text("Enter Contact Name (Optional)") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (groupName.isNotBlank() && contactName.isNotBlank()) {
                    viewModel.deleteContactFromGroup(contactName, groupName)
                    Toast.makeText(
                        context,
                        "Removed $contactName from $groupName",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(context, "Enter both Contact & Group Name!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        ) {
            Text("Remove Contact from Group")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (groupName.isNotBlank()) {
                    viewModel.deleteAllContactsInGroup(groupName)
                    Toast.makeText(
                        context,
                        "Removed all contacts from $groupName",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(context, "Please enter a group name!", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("Remove All Contacts from Group")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (groupName.isNotBlank()) {
                    viewModel.deleteGroupByName(groupName)
                    Toast.makeText(context, "Deleted Group: $groupName", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Please enter a group name!", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Delete Group")
        }
    }
}