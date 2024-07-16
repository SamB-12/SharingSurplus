package com.example.sharingsurplus.presentation.ui.dashboard.requests.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sharingsurplus.presentation.ui.components.ScaffoldComponent
import com.example.sharingsurplus.presentation.ui.components.TopAppBarWithBackComponent

@Composable
fun RequestReceivedScreen(modifier: Modifier = Modifier) {
    ScaffoldComponent(
        modifier = Modifier,
        topBar = { TopAppBarWithBackComponent(title = "Request Received", onBackClick = {})},
        content = {
            RequestReceivedScreenMinor()
        }
    )
}

@Preview
@Composable
private fun RequestReceivedScreenPreview() {
    RequestReceivedScreen()
}