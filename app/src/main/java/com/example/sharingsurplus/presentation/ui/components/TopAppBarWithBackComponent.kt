package com.example.sharingsurplus.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithBackComponent(
    modifier: Modifier = Modifier,
    title: String,
    onBackClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title)},
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = PrimaryColor)
    )
}

@Preview
@Composable
private fun TopAppBarRegistrationComponentPreview() {
    TopAppBarWithBackComponent(title = "Register")
}