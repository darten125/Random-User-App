package com.example.randomuser.presentation.ui.components.userCard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.randomuser.R
import com.example.randomuser.presentation.ui.theme.RandomUserTheme

@Composable
fun UserDetailsCard(
    userData: UserDataUiModel,
    selectedSection: UserDataType,
    onSectionSelected: (UserDataType) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(25.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 30.dp),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                UserDataType.entries.forEach { type ->
                    Box(
                        modifier = Modifier.clickable { onSectionSelected(type) }
                    ) {
                        DataSectionButton(
                            sectionType = type,
                            size = 56.dp
                        )
                        if (selectedSection == type) {
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .background(
                                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f),
                                        shape = androidx.compose.foundation.shape.CircleShape
                                    )
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .height(350.dp)
                    .padding(top = 24.dp)
            ) {
                UnifiedDataSection(
                    sectionType = selectedSection,
                    userData = userData
                )
            }
        }
    }
}

@Composable
private fun DataSectionButton(
    sectionType: UserDataType,
    size: Dp,
    ) {
    Box(
        modifier = Modifier
            .size(size)
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = when (sectionType) {
                UserDataType.PERSON -> painterResource(R.drawable.user_button)
                UserDataType.PHONE -> painterResource(R.drawable.phone_button)
                UserDataType.EMAIL -> painterResource(R.drawable.email_button)
                UserDataType.LOCATION -> painterResource(R.drawable.location_button)
            },
            tint = MaterialTheme.colorScheme.onSecondary,
            contentDescription = "sectionType",
            modifier = Modifier.size(size * 0.8f)
        )
    }
}

@Composable
private fun UnifiedDataSection(
    sectionType: UserDataType,
    userData: UserDataUiModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        when (sectionType) {
            UserDataType.PERSON -> {
                InfoRow("First name", userData.personData.firstName)
                InfoRow("Last name", userData.personData.lastName)
                InfoRow("Gender", userData.personData.gender)
                InfoRow("Age", userData.personData.age.toString())
                InfoRow("Date of birth", userData.personData.birthDate)
                InfoRow("Nationality", userData.personData.nationality.uppercase())
            }
            UserDataType.PHONE -> {
                InfoRow("Phone", userData.phoneData.phone)
                InfoRow("Cell", userData.phoneData.cell)
            }
            UserDataType.EMAIL -> {
                InfoRow("Email", userData.email)
            }
            UserDataType.LOCATION -> {
                InfoRow("Country", userData.locationData.country)
                InfoRow("State", userData.locationData.state)
                InfoRow("City", userData.locationData.city)
                InfoRow("Street", userData.locationData.fullStreet)
                InfoRow("Postcode", userData.locationData.postcode)
            }
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UserDetailsCardPreview() {
    val previewUser = UserDataUiModel(
        personData = personDataUiModel(
            fullName = "Rithy Lopez",
            firstName = "Rithy",
            lastName = "Lopez",
            age = 33,
            birthDate = "1992-10-1",
            nationality = "MX",
            gender = "male"
        ),
        phoneData = phoneDataUiModel(
            phone = "(272) 790-0888",
            cell = "(489) 330-2385"
        ),
        email = "rithy.lopez@example.com",
        locationData = locationDataUiModel(
            country = "Mexico",
            city = "Tijuana",
            state = "Baja California",
            fullStreet = "Calle de la Rosa 123",
            postcode = "22000"
        ),
        urlToImage = ""
    )
    RandomUserTheme {
        UserDetailsCard(
            userData = previewUser,
            selectedSection = UserDataType.PERSON,
            onSectionSelected = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}