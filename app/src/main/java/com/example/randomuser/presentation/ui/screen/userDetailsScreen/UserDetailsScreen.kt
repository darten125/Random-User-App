package com.example.randomuser.presentation.ui.screen.userDetailsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import coil.compose.rememberAsyncImagePainter
import com.example.randomuser.R
import com.example.randomuser.presentation.ui.components.ReturnButton
import com.example.randomuser.presentation.ui.components.userCard.UserDataType
import com.example.randomuser.presentation.ui.components.userCard.UserDataUiModel
import com.example.randomuser.presentation.ui.components.userCard.UserDetailsCard
import com.example.randomuser.presentation.ui.components.userCard.locationDataUiModel
import com.example.randomuser.presentation.ui.components.userCard.personDataUiModel
import com.example.randomuser.presentation.ui.components.userCard.phoneDataUiModel
import com.example.randomuser.presentation.ui.screen.userListScreen.navigateToUserListScreen
import com.example.randomuser.presentation.ui.theme.RandomUserTheme
import com.example.randomuser.presentation.viewmodel.UserDetailsViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun UserDetailsRoot(
    navController: NavHostController,
    userId: String,
    viewModel: UserDetailsViewModel = koinViewModel(
        parameters = { parametersOf(userId) }
    ),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    UserDetailsScreen(
        state = state,
        onEvent = viewModel::onEvent,
        navigateToUserList = { navController.navigateToUserListScreen() }
    )
}

@Composable
private fun UserDetailsScreen(
    state: UserDetailsScreenState,
    onEvent: (UserDetailsUiEvent) -> Unit = {},
    navigateToUserList: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
    ) {
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
                        onClick = { onEvent(UserDetailsUiEvent.RetryLoad) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Text("Повторить")
                    }
                }
            }
            state.userDetails != null -> {
                val user = state.userDetails
                var selectedSection by remember { mutableStateOf(UserDataType.PERSON) }

                Column(modifier = Modifier
                    .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(32.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(MaterialTheme.colorScheme.primary)
                    ) {
                        ReturnButton(
                            size = 40.dp,
                            modifier = Modifier
                                .padding(15.dp)
                                .align(Alignment.TopStart),
                            onReturnClick = navigateToUserList
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            UserAvatarImage(
                                urlToImage = user.urlToImage,
                                size = 180.dp)
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    GreetingText(state.userDetails.personData.fullName)
                    UserDetailsCard(
                        userData = user,
                        selectedSection = selectedSection,
                        onSectionSelected = { selectedSection = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun UserAvatarImage(
    urlToImage: String,
    size: Dp,
) {
    val painter = if (urlToImage.isNotEmpty()) {
        rememberAsyncImagePainter(model = urlToImage)
    } else {
        painterResource(R.drawable.test)
    }
    Image(
        painter = painter,
        contentDescription = "User photo",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(size)
            .offset(y = 60.dp)
            .clip(CircleShape)
    )
}

@Composable
private fun GreetingText(fullName: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Hi how are you today?",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "I'm",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = fullName,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true, name = "User Details - Success")
@Composable
private fun UserDetailsScreenPreview() {
    RandomUserTheme {
        UserDetailsScreen(
            state = UserDetailsScreenState(
                isLoading = false,
                userDetails = UserDataUiModel(
                    personData = personDataUiModel(
                        fullName = "Maria Garcia",
                        firstName = "Maria",
                        lastName = "Garcia",
                        age = 28,
                        birthDate = "1996-05-12",
                        nationality = "ES",
                        gender = "female"
                    ),
                    phoneData = phoneDataUiModel(
                        phone = "+34 912 345 678",
                        cell = "+34 612 345 678"
                    ),
                    email = "maria.garcia@example.com",
                    locationData = locationDataUiModel(
                        country = "Spain",
                        city = "Madrid",
                        state = "Madrid",
                        fullStreet = "Calle Gran Vía 45",
                        postcode = "28013"
                    ),
                    urlToImage = ""
                )
            ),
            navigateToUserList = {}
        )
    }
}

@Preview(showBackground = true, name = "User Details - Error")
@Composable
private fun UserDetailsScreenErrorPreview() {
    RandomUserTheme {
        UserDetailsScreen(
            state = UserDetailsScreenState(
                isLoading = false,
                errorMessage = "Нет подключения к интернету"
            ),
            navigateToUserList = {}
        )
    }
}

@Preview(showBackground = true, name = "User Details - Loading")
@Composable
private fun UserDetailsScreenLoadingPreview() {
    RandomUserTheme {
        UserDetailsScreen(
            state = UserDetailsScreenState(
                isLoading = true
            ),
            navigateToUserList = {}
        )
    }
}