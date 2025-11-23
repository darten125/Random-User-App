package com.example.randomuser.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.randomuser.presentation.ui.screen.userCreateScreen.UserCreateScreenRoute
import com.example.randomuser.presentation.ui.screen.userCreateScreen.UserCreateSection
import com.example.randomuser.presentation.ui.screen.userDetailsScreen.UserDetailsSection
import com.example.randomuser.presentation.ui.screen.userListScreen.UserListSection


@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = UserCreateScreenRoute,
        modifier = modifier,
    ) {
        UserCreateSection(navController)
        UserListSection(navController)
        UserDetailsSection(navController)
    }
}