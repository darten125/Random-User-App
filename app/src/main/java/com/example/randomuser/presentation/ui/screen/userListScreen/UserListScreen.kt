package com.example.randomuser.presentation.ui.screen.userListScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.randomuser.R
import com.example.randomuser.presentation.ui.components.userListCard.UserListCard
import com.example.randomuser.presentation.viewmodel.UserListViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.randomuser.presentation.ui.components.userListCard.UserListCardUiModel
import com.example.randomuser.presentation.ui.screen.userCreateScreen.navigateToUserCreateScreen
import com.example.randomuser.presentation.ui.screen.userDetailsScreen.navigateToUserDetailsScreen
import com.example.randomuser.presentation.ui.theme.RandomUserTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserListRoot(
    navController: NavHostController,
    viewModel: UserListViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    UserListScreen(
        state = state,
        onEvent = viewModel::onEvent,
        navigateToUserDetails = { userId: String -> navController.navigateToUserDetailsScreen(userId) },
        navigateToCreateUser = { navController.navigateToUserCreateScreen() },
    )
}

@Composable
private fun UserListScreen(
    state: UserListScreenState,
    onEvent: (UserListUiEvent) -> Unit = {},
    navigateToUserDetails: (String) -> Unit,
    navigateToCreateUser: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            ActionButtons(
                size = 60.dp,
                onClearAll = { onEvent(UserListUiEvent.ClearAllUsers) },
                onCreateUser = navigateToCreateUser
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(64.dp)
                            .align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 6.dp
                    )
                }

                state.errorMessage != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ErrorOutline,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Ошибка загрузки",
                            style = MaterialTheme.typography.headlineMedium,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = state.errorMessage)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { onEvent(UserListUiEvent.RetryLoad) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onSecondary
                            )
                        ) {
                            Text("Повторить")
                        }
                    }
                }

                state.users.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Список пуст\nДобавьте первого пользователя",
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                else -> {
                    UserListContent(
                        users = state.users,
                        onUserClick = navigateToUserDetails,
                        onDeleteClick = { onEvent(UserListUiEvent.DeleteUser(it)) },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
private fun UserListContent(
    users: List<UserListCardUiModel>,
    onUserClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(users) { user ->
            UserListCard(
                userData = user,
                onClick = { onUserClick(user.id) },
                onDeleteClick = { onDeleteClick(user.id) }
            )
        }
    }
}

@Composable
private fun ActionButtons(
    size: Dp,
    onClearAll: () -> Unit,
    onCreateUser: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ClearAllUsersButton(
            size = size,
            onClearAll = onClearAll
        )
        CreateUserButton(
            size = size,
            onCreateUser = onCreateUser
        )
    }
}

@Composable
private fun CreateUserButton(
    size: Dp,
    onCreateUser: () -> Unit
) {
    FloatingActionButton(
        modifier = Modifier.size(size),
        onClick = onCreateUser,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onSecondary
    ) {
        Icon(
            painter = painterResource(R.drawable.add_button),
            contentDescription = "Добавить пользователя",
            tint = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.padding(7.dp)
        )
    }
}

@Composable
private fun ClearAllUsersButton(
    size: Dp,
    onClearAll: () -> Unit
) {
    FloatingActionButton(
        modifier = Modifier.size(size),
        onClick = onClearAll,
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Icon(
            painter = painterResource(R.drawable.delete_button),
            contentDescription = "Очистить всех",
            tint = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Preview(showBackground = true, name = "User List - Success (2 users)")
@Composable
private fun EmptyUserListScreenPreview() {
    RandomUserTheme {
        UserListScreen(
            state = UserListScreenState(
                isLoading = false,
                users = emptyList()
            ),
            navigateToUserDetails = {},
            navigateToCreateUser = {}
        )
    }
}

@Preview(showBackground = true, name = "User List - 2 users")
@Composable
private fun UserListScreenPreview() {
    RandomUserTheme {
        UserListScreen(
            state = UserListScreenState(
                isLoading = false,
                users = listOf(
                    UserListCardUiModel(
                        id = "1",
                        name = "Gabrielle Gauthier",
                        phone = "Y26 B17-2963",
                        city = "Toronto",
                        nationality = "CA",
                        urlToImage = ""
                    ),
                    UserListCardUiModel(
                        id = "2",
                        name = "Rithy Lopez",
                        phone = "(294) 785-6098",
                        city = "Los Angeles",
                        nationality = "US",
                        urlToImage = ""
                    )
                )
            ),
            navigateToUserDetails = {},
            navigateToCreateUser = {}
        )
    }
}

@Preview(showBackground = true, name = "User List - Loading")
@Composable
private fun UserListScreenLoadingPreview() {
    RandomUserTheme {
        UserListScreen(
            state = UserListScreenState(isLoading = true),
            navigateToUserDetails = {},
            navigateToCreateUser = {}
        )
    }
}

@Preview(showBackground = true, name = "User List - Error")
@Composable
private fun UserListScreenErrorPreview() {
    RandomUserTheme {
        UserListScreen(
            state = UserListScreenState(
                isLoading = false,
                errorMessage = "Ошибка чтения данных из хранилища"
            ),
            navigateToUserDetails = {},
            navigateToCreateUser = {}
        )
    }
}