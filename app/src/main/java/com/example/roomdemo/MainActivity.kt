package com.example.roomdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.roomdemo.screens.*
import com.example.roomdemo.viewmodel.ContactViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val viewModel: ContactViewModel = viewModel()

            NavHost(navController = navController, startDestination = "home") {

                composable("home") {
                    HomeScreen(navController)
                }

                composable("add_contact") {
                    AddContactScreen(navController, viewModel)
                }

                composable("contact_list") {
                    ContactListScreen(navController, viewModel)
                }

                composable("add_phone/{contactId}", arguments = listOf(navArgument("contactId") { type = NavType.IntType })) { backStackEntry ->
                    val contactId = backStackEntry.arguments?.getInt("contactId") ?: return@composable
                    AddPhoneScreen(navController, viewModel, contactId)
                }

                composable("group_list") {
                    GroupListScreen(navController, viewModel)
                }

                composable("add_group") {
                    AddGroupScreen(navController, viewModel)
                }

                composable("group_details/{groupId}", arguments = listOf(navArgument("groupId") { type = NavType.IntType })) { backStackEntry ->
                    val groupId = backStackEntry.arguments?.getInt("groupId") ?: return@composable
                    GroupDetailsScreen(navController, viewModel, groupId)
                }

                composable("contact_group") {
                    ManageGroupContactsScreen(navController, viewModel)
                }

                composable("add_contact_to_group/{groupId}", arguments = listOf(navArgument("groupId") { type = NavType.IntType })) { backStackEntry ->
                    val groupId = backStackEntry.arguments?.getInt("groupId") ?: return@composable
                    AddContactToGroupScreen(navController, viewModel, groupId)
                }

                composable("contact_groups/{contactId}", arguments = listOf(navArgument("contactId") { type = NavType.IntType })) { backStackEntry ->
                    val contactId = backStackEntry.arguments?.getInt("contactId") ?: return@composable
                    ContactGroupsScreen(navController, viewModel, contactId)
                }
            }
        }
    }
}