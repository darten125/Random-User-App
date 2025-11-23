package com.example.randomuser.presentation.ui.screen.userDetailsScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class UserDetailsScreenRoute(
    val userId: String
)

fun NavController.navigateToUserDetailsScreen(userId: String, navOptions: NavOptions? = null) {
    navigate(route = UserDetailsScreenRoute(userId), navOptions = navOptions)
}

fun NavGraphBuilder.UserDetailsSection(
    navController: NavHostController
) {
    composable<UserDetailsScreenRoute> {backStackEntry ->
        val args = backStackEntry.toRoute<UserDetailsScreenRoute>()
        UserDetailsRoot(
            navController = navController,
            userId = args.userId
        )
    }
}
