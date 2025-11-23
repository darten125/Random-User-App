package com.example.randomuser.presentation.ui.screen.userListScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object UserListScreenRoute

fun NavController.navigateToUserListScreen(navOptions: NavOptions? = null) {
    navigate(route = UserListScreenRoute, navOptions = navOptions)
}

fun NavGraphBuilder.UserListSection(
    navController: NavHostController
) {
    composable<UserListScreenRoute> {
        UserListRoot(navController)
    }
}