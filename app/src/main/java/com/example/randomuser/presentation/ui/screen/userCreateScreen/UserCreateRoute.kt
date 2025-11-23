package com.example.randomuser.presentation.ui.screen.userCreateScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object UserCreateScreenRoute

fun NavController.navigateToUserCreateScreen(navOptions: NavOptions? = null) {
    navigate(route = UserCreateScreenRoute, navOptions = navOptions)
}

fun NavGraphBuilder.UserCreateSection(
    navController: NavHostController
) {
    composable<UserCreateScreenRoute> {
        UserCreateRoot(navController)
    }
}