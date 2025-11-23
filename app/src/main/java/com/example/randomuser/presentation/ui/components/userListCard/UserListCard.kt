package com.example.randomuser.presentation.ui.components.userListCard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import coil.compose.rememberAsyncImagePainter
import com.example.randomuser.R
import com.example.randomuser.presentation.ui.theme.RandomUserTheme

@Composable
fun UserListCard(
    userData: UserListCardUiModel,
    onClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable {
                onClick(userData.id)
            },
        shape = RoundedCornerShape(25.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 30.dp)
                    .padding(horizontal = 15.dp, vertical = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                UserImage(
                    urlToImage = userData.urlToImage,
                    size =120.dp
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(vertical = 12.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = userData.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = userData.phone,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "${userData.city}, ${userData.nationality}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            DeleteUserButton(
                id = userData.id,
                size = 25.dp,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp),
                onDeleteClick = onDeleteClick,
            )
        }
    }
}

@Composable
private fun UserImage(
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
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(12.dp))
    )
}

@Composable
fun DeleteUserButton(
    id: String,
    size: Dp,
    modifier: Modifier,
    onDeleteClick: (String) -> Unit,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .clickable {
                onDeleteClick(id)
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.delete_button),
            tint = MaterialTheme.colorScheme.onSecondary,
            contentDescription = "Удалить",
            modifier = Modifier.size(size * 0.6f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserListCardContentPreview() {
    val userData = UserListCardUiModel(
        id = "1",
        name = "Troy Ramirez",
        phone = "+7 (464) 445-5537",
        city = "New York",
        nationality = "US",
        urlToImage = ""
    )
    RandomUserTheme {
        UserListCard(
            userData = userData,
            onClick = {},
            onDeleteClick = {}
        )
    }
}
