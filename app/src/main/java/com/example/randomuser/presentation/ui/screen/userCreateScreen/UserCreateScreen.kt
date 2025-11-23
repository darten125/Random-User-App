package com.example.randomuser.presentation.ui.screen.userCreateScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.randomuser.presentation.ui.components.ReturnButton
import com.example.randomuser.presentation.ui.components.nationalitySpinner.NationalitySpinner
import com.example.randomuser.presentation.ui.screen.userListScreen.navigateToUserListScreen
import com.example.randomuser.presentation.ui.theme.RandomUserTheme
import com.example.randomuser.presentation.viewmodel.UserCreateViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserCreateRoot(
    navController: NavHostController,
    viewModel: UserCreateViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    UserCreateScreen(
        state = state,
        onEvent = viewModel::onEvent,
        navigateToUserList = { navController.navigateToUserListScreen()}
    )
}

@Composable
private fun UserCreateScreen(
    state: UserCreateScreenState,
    onEvent: (UserCreateUiEvent) -> Unit = {},
    navigateToUserList: () -> Unit
) {
    if (state.isUserCreated) {
        LaunchedEffect(Unit) {
            navigateToUserList()
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(horizontal = 15.dp, vertical = 15.dp)
        ) {
            ReturnButton(
                size = 40.dp,
                modifier = Modifier.align(Alignment.CenterStart),
                onReturnClick = navigateToUserList
            )
            Text(
                text = "Создать пользователя",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(72.dp)
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 6.dp
                )
            }

            state.errorMessage != null -> {
                ErrorContent(
                    message = state.errorMessage,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                )
            }
            else -> {
                CreateUserContent(
                    selectedGender = state.selectedGender,
                    selectedNationality = state.selectedNationality,
                    onGenderSelected = { onEvent(UserCreateUiEvent.SelectGender(it)) },
                    onNationalitySelected = { onEvent(UserCreateUiEvent.SelectNationality(it)) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 80.dp)
                )
            }
        }
        Button(
            onClick = { onEvent(UserCreateUiEvent.CreateUser) },
            enabled = state.selectedGender != null && state.selectedNationality != null,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            )
        ) {
            Text("Создать пользователя")
        }
    }
}

@Composable
private fun CreateUserContent(
    selectedGender: String?,
    selectedNationality: String?,
    onGenderSelected: (String) -> Unit,
    onNationalitySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Пол", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                GenderButton("Мужской", selectedGender == "male") { onGenderSelected("male") }
                GenderButton("Женский", selectedGender == "female") { onGenderSelected("female") }
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Национальность", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            NationalitySpinner(
                selectedNationality = selectedNationality,
                onNationalitySelected = onNationalitySelected,
                modifier = Modifier.width(280.dp)
            )
        }
    }
}

@Composable
private fun ErrorContent(
    message: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
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
            text = "Ошибка",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun GenderButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
            )
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview(showBackground = true, name = "Create User - Initial")
@Composable
private fun UserCreateScreenInitialPreview() {
    RandomUserTheme {
        UserCreateScreen(
            state = UserCreateScreenState(),
            navigateToUserList = {}
        )
    }
}

@Preview(showBackground = true, name = "Create User - Gender Selected")
@Composable
private fun UserCreateScreenGenderSelectedPreview() {
    RandomUserTheme {
        UserCreateScreen(
            state = UserCreateScreenState(
                selectedGender = "male",
                selectedNationality = null
            ),
            navigateToUserList = {}
        )
    }
}

@Preview(showBackground = true, name = "Create User - Both Selected")
@Composable
private fun UserCreateScreenBothSelectedPreview() {
    RandomUserTheme {
        UserCreateScreen(
            state = UserCreateScreenState(
                selectedGender = "female",
                selectedNationality = "FR"
            ),
            navigateToUserList = {}
        )
    }
}

@Preview(showBackground = true, name = "Create User - Error")
@Composable
private fun UserCreateScreenErrorPreview() {
    RandomUserTheme {
        UserCreateScreen(
            state = UserCreateScreenState(
                errorMessage = "Не удалось создать пользователя"
            ),
            navigateToUserList = {}
        )
    }
}