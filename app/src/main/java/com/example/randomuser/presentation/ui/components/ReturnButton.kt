package com.example.randomuser.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.randomuser.R
import com.example.randomuser.presentation.ui.theme.RandomUserTheme

@Composable
fun ReturnButton(
    size: Dp,
    modifier : Modifier,
    onReturnClick: () -> Unit,
    ) {
    Box(
        modifier = modifier
            .size(size)
            .background(
                color = MaterialTheme.colorScheme.onSecondary,
                shape = androidx.compose.foundation.shape.CircleShape
            )
            .clickable {
                onReturnClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.arrow_back_button),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = "Вернуться",
        )
    }
}

@Preview(showBackground = false)
@Composable
fun ReturnButtonPreview() {
    RandomUserTheme {
        ReturnButton(
            size = 80.dp,
            modifier = Modifier,
            onReturnClick = {}
        )
    }
}