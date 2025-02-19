package com.example.roomdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.roomdemo.screens.AddContactScreen
import com.example.roomdemo.screens.AddContactToGroupScreen
import com.example.roomdemo.screens.AddGroupScreen
import com.example.roomdemo.screens.AddPhoneScreen
import com.example.roomdemo.screens.ContactDeletionScreen
import com.example.roomdemo.screens.ContactGroupsScreen
import com.example.roomdemo.screens.ContactListScreen
import com.example.roomdemo.screens.GroupDeletionScreen
import com.example.roomdemo.screens.GroupDetailsScreen
import com.example.roomdemo.screens.GroupListScreen
import com.example.roomdemo.screens.HomeScreen
import com.example.roomdemo.screens.ManageGroupContactsScreen
import com.example.roomdemo.viewmodel.ContactViewModel
import com.example.roomdemo.viewmodel.ContactViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val viewModel: ContactViewModel =
                viewModel(factory = ContactViewModelFactory(application))

            NavHost(navController = navController, startDestination = "home") {

                composable("home") {
                    HomeScreen(navController, viewModel)
                }

                composable("add_contact") {
                    AddContactScreen(navController, viewModel)
                }

                composable("contact_list") {
                    ContactListScreen(navController, viewModel)
                }

                composable(
                    "add_phone/{contactId}",
                    arguments = listOf(navArgument("contactId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val contactId =
                        backStackEntry.arguments?.getInt("contactId") ?: return@composable
                    AddPhoneScreen(navController, viewModel, contactId)
                }

                composable("group_list") {
                    GroupListScreen(navController, viewModel)
                }

                composable("add_group") {
                    AddGroupScreen(navController, viewModel)
                }

                composable(
                    "group_details/{groupId}",
                    arguments = listOf(navArgument("groupId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val groupId = backStackEntry.arguments?.getInt("groupId") ?: return@composable
                    GroupDetailsScreen(navController, viewModel, groupId)
                }

                composable("contact_group") {
                    ManageGroupContactsScreen(navController, viewModel)
                }

                composable(
                    "add_contact_to_group/{groupId}",
                    arguments = listOf(navArgument("groupId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val groupId = backStackEntry.arguments?.getInt("groupId") ?: return@composable
                    AddContactToGroupScreen(navController, viewModel, groupId)
                }

                composable(
                    "contact_groups/{contactId}",
                    arguments = listOf(navArgument("contactId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val contactId =
                        backStackEntry.arguments?.getInt("contactId") ?: return@composable
                    ContactGroupsScreen(navController, viewModel, contactId)
                }

                composable("contact_delete") {
                    ContactDeletionScreen(viewModel)
                }

                composable("group_delete") {
                    GroupDeletionScreen(viewModel)
                }
            }
        }
    }
}