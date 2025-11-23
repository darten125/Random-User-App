package com.example.randomuser.presentation.ui.components.nationalitySpinner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.randomuser.presentation.ui.theme.RandomUserTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NationalitySpinner(
    selectedNationality: String?,
    onNationalitySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = NationalityType.entries
                .find { it.code == selectedNationality }
                ?.toString() ?: "Выберите национальность",
            onValueChange = { },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            NationalityType.entries.forEach { nat ->
                DropdownMenuItem(
                    text = { Text(nat.nationality) },
                    onClick = {
                        onNationalitySelected(nat.code)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Nationality Dropdown - Nothing selected")
@Composable
private fun NationalityDropdownEmptyPreview() {
    RandomUserTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                NationalitySpinner(
                    selectedNationality = null,
                    onNationalitySelected = {},
                    modifier = Modifier.width(280.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Nationality Dropdown - USA selected")
@Composable
private fun NationalityDropdownUSASelectedPreview() {
    RandomUserTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                NationalitySpinner(
                    selectedNationality = "US",
                    onNationalitySelected = {},
                    modifier = Modifier.width(280.dp)
                )
            }
        }
    }
}
