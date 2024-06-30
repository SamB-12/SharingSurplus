package com.example.sharingsurplus.presentation.ui.dashboard.produce.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sharingsurplus.presentation.ui.components.ScaffoldComponent
import com.example.sharingsurplus.presentation.ui.components.TopAppBarWithBackComponent

@Composable
fun AddProduceScreen(modifier: Modifier = Modifier) {
    ScaffoldComponent(
        modifier = Modifier,
        topBar = { TopAppBarWithBackComponent(title = "Add a produce")},
        content = { AddScreenMinor()}
    )
}

@Preview
@Composable
private fun AddProduceScreenPreview() {
    AddProduceScreen()
}